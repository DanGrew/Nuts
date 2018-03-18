package uk.dangrew.nuts.graphics.label;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.label.Labelables;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.Concept;

public class UiLabelController {
   
   private final Database database;
   
   private final ObservableList< Concept > privateSelectedDatabaseConcepts;
   private final ObservableList< Concept > publicSelectedDatabaseConcepts;
   private final ObservableList< Concept > privateSelectedLabelConcepts;
   private final ObservableList< Concept > publicSelectedLabelConcepts;
   
   private final FunctionListChangeListenerImpl< Concept > labelListener;
   private final ObjectProperty< Label > selectedLabel;
   
   private ObservableList< Concept > labelLiveSelection;
   private ObservableList< Concept > databaseLiveSelection;
   
   private Labelables selectedLabelable;
   
   public UiLabelController( Database database ) {
      this.database = database;
      this.privateSelectedDatabaseConcepts = FXCollections.observableArrayList();
      this.privateSelectedLabelConcepts = FXCollections.observableArrayList();
      this.publicSelectedDatabaseConcepts = new PrivatelyModifiableObservableListImpl<>( privateSelectedDatabaseConcepts );
      this.publicSelectedLabelConcepts = new PrivatelyModifiableObservableListImpl<>( privateSelectedLabelConcepts );
      this.selectedLabel = new SimpleObjectProperty<>();
      this.labelListener = new FunctionListChangeListenerImpl<>( c -> refreshSelection(), c -> refreshSelection() );
   }//End Constructor
   
   private void sort( ObservableList< Concept > concepts ) {
      concepts.sort( Comparators.stringExtractionComparater( c -> c.properties().nameProperty().get() ) );
   }//End Method

   public void monitorLabelSelection( ObservableList< Concept > labelSelection ) {
      this.labelLiveSelection = labelSelection;
   }//End Method
   
   private void refreshSelection(){
      selectLabel( selectedLabel.get() );
   }//End Method
   
   public ReadOnlyObjectProperty< Label > selectedLabel() {
      return selectedLabel;
   }//End Method
   
   public void selectLabel( Label label ) {
      privateSelectedLabelConcepts.clear();
      if ( selectedLabel.get() != null ) {
         selectedLabel.get().concepts().removeListener( labelListener );
      }
      
      selectedLabel.set( label );
      if ( selectedLabel.get() != null ) {
         selectedLabel.get().concepts().addListener( labelListener );
      }
      selectLabelable( selectedLabelable );
      
      if ( selectedLabel.get() == null ) {
         return;
      }
      
      privateSelectedLabelConcepts.addAll( selectedLabel.get().concepts() );
      sort( privateSelectedLabelConcepts );
   }//End Method
   
   public ObservableList< Concept > selectedLabelConcepts() {
      return publicSelectedLabelConcepts;
   }//End Method
   
   public void monitorDatabaseSelection( ObservableList< Concept > databaseSelection ) {
      this.databaseLiveSelection = databaseSelection;
   }//End Method
   
   public void selectLabelable( Labelables labelable ) {
      privateSelectedDatabaseConcepts.clear();
      selectedLabelable = labelable;
      
      if ( selectedLabelable == null ) {
         return;
      }
      
      List< Concept > databaseConcepts = new ArrayList<>( labelable.redirect( database ).objectList() );
      if ( selectedLabel.get() != null ) {
         databaseConcepts.removeIf( c -> selectedLabel.get().concepts().contains( c ) );
      }
      
      privateSelectedDatabaseConcepts.addAll( databaseConcepts );
      sort( privateSelectedDatabaseConcepts );
   }//End Method
   
   public ObservableList< Concept > selectedDatabaseConcepts() {
      return publicSelectedDatabaseConcepts;
   }//End Method
   
   public void removeFromLabel() {
      if ( selectedLabel.get() == null ) {
         return;
      }
      
      selectedLabel.get().concepts().removeAll( labelLiveSelection );
   }//End Method

   public void addToLabel() {
      if ( selectedLabel.get() == null ) {
         return;
      }
      
      selectedLabel.get().concepts().addAll( databaseLiveSelection );
   }//End Method

   public void changeName( String text ) {
      if ( selectedLabel.get() == null || text.trim().isEmpty() ) {
         return;
      }
      
      selectedLabel.get().properties().nameProperty().set( text );
   }//End Method

   public void createLabel( String text ) {
      if ( text.trim().isEmpty() ) {
         return;
      }
      
      Label label = database.labels().createConcept( text );
      selectLabel( label );
   }//End Method

   public void deleteLabel() {
      if ( selectedLabel.get() == null ) {
         return;
      }
      database.labels().removeConcept( selectedLabel.get() );
      selectLabel( null );
   }//End Method
}//End Class
