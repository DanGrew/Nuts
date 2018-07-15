package uk.dangrew.nuts.graphics.deletion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.ButtonType;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.FoodReferenceChecker;

public class FoodDeletionMechanismTest {

   private Food deletion;
   private Food replacement;
   private Food reference;
   
   @Mock private UiFoodSelectionDialog selectionDialog;
   @Mock private UiFoodDeletionConfirmAlert deletionConfirmAlert;
   @Mock private UiFoodReplacementConfirmAlert replacementConfirmAlert;
   @Mock private FoodReferenceChecker referenceChecker;
   private FoodDeletionMechanism systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      deletion = new FoodItem( "Deletion" );
      replacement = new FoodItem( "Replacement" );
      reference = new Meal( "Reference" );
      systemUnderTest = new FoodDeletionMechanism( 
               deletionConfirmAlert,
               replacementConfirmAlert,
               selectionDialog, 
               referenceChecker 
      );
   }//End Method

   @Test public void selectAndAccept() {
      when( referenceChecker.lastSearchResult() ).thenReturn( Arrays.asList( reference ) );
      when( deletionConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.OK ) );
      when( replacementConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( UiFoodReplacementConfirmAlert.REPLACE ) );
      when( selectionDialog.friendly_showAndWait() ).thenReturn( Optional.of( replacement ) );
      
      assertThat( systemUnderTest.delete( deletion ), is( true ) );
      
      verify( referenceChecker ).searchFor( deletion );
      verify( deletionConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).setUsageInformation( Arrays.asList( reference ) );
      verify( selectionDialog ).friendly_showAndWait();
      verify( referenceChecker ).replaceWith( replacement );
      verify( referenceChecker, never() ).removeReferences();
   }//End Method
   
   @Test public void noReferences(){
      when( referenceChecker.lastSearchResult() ).thenReturn( Arrays.asList() );
      when( deletionConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.OK ) );
      when( replacementConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( UiFoodReplacementConfirmAlert.DELETE ) );
      when( selectionDialog.friendly_showAndWait() ).thenReturn( Optional.of( replacement ) );
      
      assertThat( systemUnderTest.delete( deletion ), is( true ) );
      
      verify( referenceChecker ).searchFor( deletion );
      verify( deletionConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert, never() ).friendly_showAndWait();
      verify( selectionDialog, never() ).friendly_showAndWait();
      verify( referenceChecker, never() ).replaceWith( Mockito.any() );
      verify( referenceChecker ).removeReferences();
   }//End Method
   
   @Test public void cancelFoodSelection(){
      when( referenceChecker.lastSearchResult() ).thenReturn( Arrays.asList( reference ) );
      when( deletionConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.OK ) );
      when( replacementConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( UiFoodReplacementConfirmAlert.REPLACE ) );
      when( selectionDialog.friendly_showAndWait() ).thenReturn( Optional.empty() );
      
      assertThat( systemUnderTest.delete( deletion ), is( false ) );
      
      verify( referenceChecker ).searchFor( deletion );
      verify( deletionConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).setUsageInformation( Arrays.asList( reference ) );
      verify( selectionDialog ).friendly_showAndWait();
      verify( referenceChecker, never() ).replaceWith( Mockito.any() );
      verify( referenceChecker, never() ).removeReferences();
   }//End Method
   
   @Test public void noReplacement(){
      when( referenceChecker.lastSearchResult() ).thenReturn( Arrays.asList( reference ) );
      when( deletionConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.OK ) );
      when( replacementConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( UiFoodReplacementConfirmAlert.DELETE ) );
      when( selectionDialog.friendly_showAndWait() ).thenReturn( Optional.of( replacement ) );
      
      assertThat( systemUnderTest.delete( deletion ), is( true ) );
      
      verify( referenceChecker ).searchFor( deletion );
      verify( deletionConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).setUsageInformation( Arrays.asList( reference ) );
      verify( selectionDialog, never() ).friendly_showAndWait();
      verify( referenceChecker, never() ).replaceWith( Mockito.any() );
      verify( referenceChecker ).removeReferences();
   }//End Method
   
   @Test public void cancelReplacement(){
      when( referenceChecker.lastSearchResult() ).thenReturn( Arrays.asList( reference ) );
      when( deletionConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.OK ) );
      when( replacementConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.CANCEL ) );
      when( selectionDialog.friendly_showAndWait() ).thenReturn( Optional.of( replacement ) );
      
      assertThat( systemUnderTest.delete( deletion ), is( false ) );
      
      verify( referenceChecker ).searchFor( deletion );
      verify( deletionConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert ).setUsageInformation( Arrays.asList( reference ) );
      verify( selectionDialog, never() ).friendly_showAndWait();
      verify( referenceChecker, never() ).replaceWith( Mockito.any() );
      verify( referenceChecker, never() ).removeReferences();
   }//End Method
   
   @Test public void noDeletion(){
      when( referenceChecker.lastSearchResult() ).thenReturn( Arrays.asList( reference ) );
      when( deletionConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.CANCEL ) );
      when( selectionDialog.friendly_showAndWait() ).thenReturn( Optional.of( replacement ) );
      
      assertThat( systemUnderTest.delete( deletion ), is( false ) );
      
      verify( referenceChecker, never() ).searchFor( deletion );
      verify( deletionConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert, never() ).friendly_showAndWait();
      verify( selectionDialog, never() ).friendly_showAndWait();
      verify( referenceChecker, never() ).replaceWith( Mockito.any() );
      verify( referenceChecker, never() ).removeReferences();
   }//End Method
   
   @Test public void cancelDeletion(){
      when( referenceChecker.lastSearchResult() ).thenReturn( Arrays.asList( reference ) );
      when( deletionConfirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.CANCEL ) );
      when( selectionDialog.friendly_showAndWait() ).thenReturn( Optional.of( replacement ) );
      
      assertThat( systemUnderTest.delete( deletion ), is( false ) );
      
      verify( referenceChecker, never() ).searchFor( deletion );
      verify( deletionConfirmAlert ).friendly_showAndWait();
      verify( replacementConfirmAlert, never() ).friendly_showAndWait();
      verify( selectionDialog, never() ).friendly_showAndWait();
      verify( referenceChecker, never() ).replaceWith( Mockito.any() );
      verify( referenceChecker, never() ).removeReferences();
   }//End Method

}//End Class
