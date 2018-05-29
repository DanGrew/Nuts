package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.List;
import java.util.function.Function;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Pair;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class FoodTableManipulator< FoodT extends Food > extends TableManipulator< ConceptTableRow< FoodT >, FoodRowManipulator< FoodT > >{

   private final Function< FoodT, Food > foodConceptMapper;
   
   public FoodTableManipulator( 
            TableView< ConceptTableRow< FoodT > > table
   ) {
      this( 
               table, 
               f -> f
      );
   }//End Constructor
   
   protected FoodTableManipulator( 
            TableView< ConceptTableRow< FoodT > > table,
            Function< FoodT, Food > foodConceptMapper
   ) {
      super( table, FoodRowManipulator< FoodT >::new );
      this.foodConceptMapper = foodConceptMapper;
   }//End Constructor
   
   public FoodRowManipulator< FoodT > findRow( String name ) {
      return findRow( name, 1 );
   }//End Method
   
   public FoodRowManipulator< FoodT > findRow( String name, int instance ) {
      List< TableRow< ConceptTableRow< FoodT > > > rows = hacks.lookupTableRows( table );
      return rowManipulatorOf( find( rows, name, instance ) );
   }//End Method
   
   private Pair< TableRow< ConceptTableRow< FoodT > >, Integer > find( List< TableRow< ConceptTableRow< FoodT > > > rows, String name, int instance ) {
      int counter = instance;
      for ( int i = 0; i < rows.size(); i++ ) {
         TableRow< ConceptTableRow< FoodT > > row = rows.get( i );
         if ( nameFilter( row, name ) ) {
            if ( counter == 1 ) {
               return new Pair<>( row, i );
            } else {
               counter--;
            }
         }
      }
      return null;
   }//End Method
   
   private boolean nameFilter( TableRow< ConceptTableRow< FoodT > > row, String name ) {
      return foodConceptMapper.apply( row.getItem().concept() ).properties().nameProperty().get().equals( name );
   }//End Method
   
}//End Class
