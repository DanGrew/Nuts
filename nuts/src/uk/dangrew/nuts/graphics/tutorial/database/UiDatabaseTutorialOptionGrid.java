package uk.dangrew.nuts.graphics.tutorial.database;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public class UiDatabaseTutorialOptionGrid extends BorderPane {

   public UiDatabaseTutorialOptionGrid( TutorialSelector selector, DatabaseTutorials... tutorials ) {
      GridPane content = new GridPane();
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( content, 1 );
      styling.configureConstraintsForEvenRows( content, tutorials.length );
      
      for ( int i = 0; i < tutorials.length; i++ ) {
         DatabaseTutorials tutorial = tutorials[ i ];
         Button button = new Button( tutorial.description() );
         button.setMaxWidth( Double.MAX_VALUE );
         button.setOnAction( e -> selector.startTutorial( tutorial ) );
         content.add( button, 0, i );
      }

      Label header = styling.createBoldLabel( "Tutorials available..." );
      header.setPadding( new Insets( 5, 0, 10, 0 ) );
      this.setTop( header );
      this.setCenter( content );
      this.setPadding( new Insets( 5 ) );
   }//End Constructor
   
}//End Class
