package uk.dangrew.nuts.graphics.database;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.store.DatabaseType;
import uk.dangrew.nuts.system.ConceptStore;
import uk.dangrew.nuts.template.Template;

public enum FoodTypes {

   FoodItems( DatabaseType.FoodItems ),
   Meals( DatabaseType.Meals ),
   Templates( DatabaseType.Templates );
   
   private final DatabaseType type;
   
   private FoodTypes( DatabaseType type ) {
      this.type = type;
   }//End Constructor
   
   public ConceptStore< Food > redirect( Database database ) {
      return type.redirect( database );
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
