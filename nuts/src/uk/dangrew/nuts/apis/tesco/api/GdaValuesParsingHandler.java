package uk.dangrew.nuts.apis.tesco.api;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.item.GuidelineDailyAmount;
import uk.dangrew.nuts.apis.tesco.item.GuidelineDailyAmounts;

public class GdaValuesParsingHandler {

   private GuidelineDailyAmounts currentGdas;
   
   private String name;
   private List< String > amount;
   private String percent;
   private String rating;
   
   public GdaValuesParsingHandler() {
      this.amount = new ArrayList<>();
   }//End Constructor
   
   public void setCurrentGdas( GuidelineDailyAmounts gdas ) {
      this.currentGdas = gdas;
   }//End Method
   
   public void addValueForArrayValue( String key, String value ) {
      amount.add( value );
   }//End Method
   
   public void handleValuesStartObject( String key ) {
      name = null;
      amount.clear();
      percent = null;
      rating = null;
   }//End Method
   
   public void handleValuesEndObject( String key ) {
      if ( name.equalsIgnoreCase( "Energy" ) ) {
         String kj = amount.stream().filter( v -> v.contains( "kJ" ) ).findFirst().orElse( null );
         if ( kj != null ) {
            currentGdas.energyGda().energyInKj().set( kj );
         }
         String kcal = amount.stream().filter( v -> v.contains( "kcal" ) ).findFirst().orElse( null );
         if ( kcal != null ) {
            currentGdas.energyGda().energyInKcal().set( kcal );
         }
         currentGdas.energyGda().percent().set( percent );
         currentGdas.energyGda().rating().set( rating );
      } else if ( name.equalsIgnoreCase( "Fat" ) ) {
         setGda( currentGdas.fatGda() );
      } else if ( name.equalsIgnoreCase( "Saturates" ) ) {
         setGda( currentGdas.saturatesGda() );
      } else if ( name.equalsIgnoreCase( "Sugars" ) ) {
         setGda( currentGdas.sugarsGda() );
      } else if ( name.equalsIgnoreCase( "Salt" ) ) {
         setGda( currentGdas.saltGda() );
      } else {
         System.out.println( "Detected new Gda Value: " + name );
      }
   }//End Method
   
   private void setGda( GuidelineDailyAmount gda ) {
      gda.amount().set( amount.get( 0 ) );
      gda.percent().set( percent );
      gda.rating().set( rating );
   }//End Method
   
   public void handleValuesStartArray( String key ) {
      //do nothing
   }//End Method
   
   public void handleValuesEndArray( String key ) {
      //do nothing      
   }//End Method
   
   public void setName( String key, String value ) {
      this.name = value;
   }//End Method
   
   public void setPercent( String key, String value ) {
      this.percent = value;
   }//End Method
   
   public void setRating( String key, String value ) {
      this.rating = value;
   }//End Method
   
}//End Class
