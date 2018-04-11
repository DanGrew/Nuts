package uk.dangrew.nuts.graphics.database;

import java.util.function.Function;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;
import uk.dangrew.nuts.template.Template;

public enum FoodTypes {

   FoodItems( Database::foodItems ),
   Meals( Database::meals ),
   Templates( Database::templates );
   
   private final Function< Database, ConceptStore< ? extends Food > > conceptRedirect;
   
   private FoodTypes( Function< Database, ConceptStore< ? extends Food > > conceptRedirect ) {
      this.conceptRedirect = conceptRedirect;
   }//End Constructor
   
   public ConceptStore< Food > redirect( Database database ) {
      return ( ConceptStore< Food > )conceptRedirect.apply( database );
   }//End Method
   
   public static FoodTypes typeOf( Food food ) {
      if ( food instanceof FoodItem ) {
         return FoodItems;
      } else if ( food instanceof Template ) {
         return Templates;
      } else if ( food instanceof Meal ) {
         return Meals;
      } else return null;
   }//End Method

}//End Enum
