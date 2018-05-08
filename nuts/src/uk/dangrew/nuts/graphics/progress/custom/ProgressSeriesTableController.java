package uk.dangrew.nuts.graphics.progress.custom;

import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.system.ConceptStore;

public class ProgressSeriesTableController extends GeneralConceptTableController< ProgressSeries > {

   private final GraphSeriesVisibility seriesVisibility;
   
   public ProgressSeriesTableController( 
            ConceptStore< ProgressSeries > foods, 
            GraphSeriesVisibility seriesVisibility 
   ) {
      super( foods );
      this.seriesVisibility = seriesVisibility;
   }//End Class
   
   @Override public void removeSelectedConcept() {
      ConceptTableRow< ProgressSeries > selection = table().getSelectionModel().getSelectedItem();
      if ( selection != null ) {
         seriesVisibility.hide( selection.concept() );
      }
      
      super.removeSelectedConcept();
   }//End Method

}//End Class
