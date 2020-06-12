package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanController;
import uk.dangrew.nuts.food.FoodPortion;

public class TreeTableRootItem extends TreeItem< TreeTableController<FoodPortion> >{

   public TreeTableRootItem( DayPlan concept, DayPlanController controller ) {
      setValue( new TreeTableRootController( concept, this, controller ) );
      setExpanded( true );
   }//End Constructor
   
}//End Class
