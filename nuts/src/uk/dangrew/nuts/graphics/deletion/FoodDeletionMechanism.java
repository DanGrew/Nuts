package uk.dangrew.nuts.graphics.deletion;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.store.FoodReferenceChecker;

public class FoodDeletionMechanism {
   
   private final UiFoodDeletionConfirmAlert delectionConfirm;
   private final UiFoodReplacementConfirmAlert replacementConfirm;
   private final UiFoodSelectionDialog selectionDialog;
   private final FoodReferenceChecker referenceChecker;

   public FoodDeletionMechanism( Database database ) {
      this( 
               new UiFoodDeletionConfirmAlert(),
               new UiFoodReplacementConfirmAlert(),
               new UiFoodSelectionDialog( database ), 
               new FoodReferenceChecker( database ) 
      );
   }//End Constructor
   
   FoodDeletionMechanism( 
            UiFoodDeletionConfirmAlert deletionConfirmAlert, 
            UiFoodReplacementConfirmAlert replacementConfirmAlert, 
            UiFoodSelectionDialog selectionDialog, 
            FoodReferenceChecker referenceChecker 
   ) {
      this.delectionConfirm = deletionConfirmAlert;
      this.replacementConfirm = replacementConfirmAlert;
      this.selectionDialog = selectionDialog;
      this.referenceChecker = referenceChecker;
   }//End Constructor

   public boolean delete( Food deletion ) {
      Optional< ButtonType > deletionResult = delectionConfirm.friendly_showAndWait();
      if ( deletionResult.get() != ButtonType.OK ) {
         return false;
      }
      
      referenceChecker.searchFor( deletion );
      if ( referenceChecker.lastSearchResult().isEmpty() ) {
         referenceChecker.removeReferences();
         return true;
      }
      
      replacementConfirm.setUsageInformation( referenceChecker.lastSearchResult() );
      Optional< ButtonType > replacementResult = replacementConfirm.friendly_showAndWait();
      if ( replacementResult.get() == UiFoodReplacementConfirmAlert.DELETE ) {
         referenceChecker.removeReferences();
         return true;
      } else if ( replacementResult.get() == ButtonType.CANCEL ) {
         return false;
      }
      
      Optional< Food > foodSelection = selectionDialog.friendly_showAndWait();
      if ( !foodSelection.isPresent() ) {
         return false;
      }
      
      referenceChecker.replaceWith( foodSelection.get() );
      return true;
   }//End Method

}//End Class
