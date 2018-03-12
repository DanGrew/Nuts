package uk.dangrew.nuts.manual.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;

public class TescoExamples {

   private static final String CRAVENDALE_MILK_NAME = "Cravendale Filtered Whole Milk 2 Litre";
   private static final String CRAVENDALE_MILK_TPNC = "257265436";
   private static final String CRAVENDALE_MILK_FILE = "html-257265436-cravendale-milk.txt";
   
   public static TescoFoodDescription cravendaleMilk(){
      TescoFoodDescription description = new TescoFoodDescription( TescoExamples.CRAVENDALE_MILK_NAME );
      description.productDetail().tpncs().add( TescoExamples.CRAVENDALE_MILK_TPNC );
      return description;
   }//End Method
   
   public static Document crandaleMilkHtml(){
      return readHtmlIntoDocument( CRAVENDALE_MILK_FILE );
   }//End Method
   
   public static Document readHtmlIntoDocument( String file ) {
      String content = TestCommon.readFileIntoString( TescoExamples.class, file );
      return Jsoup.parse( content );
   }//End Method
}//End Class
