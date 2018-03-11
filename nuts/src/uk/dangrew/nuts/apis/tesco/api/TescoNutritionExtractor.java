package uk.dangrew.nuts.apis.tesco.api;

public class TescoNutritionExtractor {

   public void extract( TescoWebpageNutritionTable table, CalculatedNutritionParsingHandler model ) {
      model.setPer100Header( null, table.columnRow( 1, 0 ) );
      model.setPerServingHeader( null, table.columnRow( 2, 0 ) );
      for ( int row = 1; row < table.rowCount(); row++ ) {
         model.startedCalculatedNutrientsObject( null );
         model.setName( null, table.columnRow( 0, row ) );
         model.setValuePer100( null, table.columnRow( 1, row ) );
         model.setValuePerServing( null, table.columnRow( 2, row ) );
         model.finishedCalculatedNutrientsObject( null );
      }
   }//End Method
   

}//End Class
