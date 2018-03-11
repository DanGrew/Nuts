package uk.dangrew.nuts.apis.tesco.api;

public class TescoNutritionExtractor {

   static final String EXCLUSION_REFRENCE_INTAKE = "reference intake";
   
   public void extract( TescoWebpageNutritionTable table, CalculatedNutritionParsingHandler model ) {
      model.setPer100Header( null, table.columnRow( 1, 0 ) );
      model.setPerServingHeader( null, table.columnRow( 2, 0 ) );
      for ( int row = 1; row < table.rowCount(); row++ ) {
         String name = table.columnRow( 0, row );
         if ( isRowExclusion( name ) ) {
            continue;
         }
         
         model.startedCalculatedNutrientsObject( null );
         model.setName( null, name );
         model.setValuePer100( null, table.columnRow( 1, row ) );
         model.setValuePerServing( null, table.columnRow( 2, row ) );
         model.finishedCalculatedNutrientsObject( null );
      }
   }//End Method
   
   private boolean isRowExclusion( String rowName ) {
      String parseable = rowName.toLowerCase();
      if ( parseable.contains( EXCLUSION_REFRENCE_INTAKE ) ) {
         return true;
      }
      return false;
   }//End Method
   

}//End Class
