package uk.dangrew.nuts.graphics.progress.custom;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.kode.javafx.table.base.ConceptTableRow;
import uk.dangrew.kode.javafx.table.controller.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

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
