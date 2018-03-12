package uk.dangrew.nuts.apis.tesco.api.webpage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javafx.util.Pair;

public class TescoWebpageNutritionTable {
   
   static final String NUTRITION_TABLE_TAG = "table";
   static final String HEADER_ENTRY = "th";
   static final String ROW_ENTRY = "td";

   private final Map< Pair< Integer, Integer >, String > table;
   private int columnCount;
   private int rowCount;
   
   public TescoWebpageNutritionTable( Document webpageDocument ) {
      this();
      parse( webpageDocument );
   }//End Constructor
   
   public TescoWebpageNutritionTable() {
      this.table = new HashMap<>();
   }//End Constructor
      
   private void parse( Document webpageDocument ) {
      if ( webpageDocument == null ) {
         return;
      }
      
      Element table = webpageDocument.select( NUTRITION_TABLE_TAG ).first();
      if ( table == null ) {
         //none nutritional product
         return;
      }
      parseHeaders( table );
      parseRows( table );
   }//End Method
   
   private void parseColumnRow( Iterator< Element > iterator, int column, int row ) {
      Element element = iterator.next();
      putCellValue( column, row, element.text() );
   }//End Method
   
   private void parseHeaders( Element table ) {
      Iterator< Element > iterator = table.select( HEADER_ENTRY ).iterator();
      int headerCount = 0;
      while( iterator.hasNext() ) {
         parseColumnRow( iterator, headerCount, 0 );
         headerCount++;
      }
   }//End Method
   
   private void parseRows( Element table ) {
      Iterator< Element > iterator = table.select( ROW_ENTRY ).iterator();
      int column = 0;
      int row = 1;
      while( iterator.hasNext() ) {
         parseColumnRow( iterator, column, row );
         column++;
         
         if ( column == columnCount() ) {
            column = 0;
            row++;
         }
      }
   }//End Method
   
   private void putCellValue( int column, int row, String value ) {
      table.put( new Pair<>( column, row ), value );
      
//      System.out.println( "C: " + column + " R: " + row + " V: " + value );
      if ( column > columnCount ) {
         columnCount = column;
      }
      if ( row > rowCount ) {
         rowCount = row;
      }
   }//End Method

   public int columnCount() {
      return columnCount + 1;
   }//End Method

   public int rowCount() {
      return rowCount + 1;
   }//End Method

   public String columnRow( int column, int row ) {
      return table.get( new Pair<>( column, row ) );
   }//End Method
   
   public void modifyColumnRow( int column, int row, String value ) {
      putCellValue( column, row, value );
   }//End Method

   public void clearRow( int row ) {
      for ( int c = 0; c < columnCount(); c++ ) {
         table.remove( new Pair<>( c, row ) );
      }
   }//End Method

   public void clearColumn( int column ) {
      for ( int r = 0; r < rowCount(); r++ ) {
         table.remove( new Pair<>( column, r ) );
      }
   }//End Method

}//End Class
