package uk.dangrew.nuts.configuration;

import java.util.EnumMap;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutsSettings {

   private final NutsSettingsRegistrations registrations;
   private final EnumMap< NutritionalUnit, ObjectProperty< Boolean > > showingUnitProperties;
   
   public NutsSettings() {
      this.showingUnitProperties = new EnumMap<>( NutritionalUnit.class );
      Stream.of( NutritionalUnit.values() ).forEach( u -> showingUnitProperties.put( u, new SimpleObjectProperty<>( false ) ) );
      this.configureDefaults();
      
      this.registrations = new NutsSettingsRegistrations( this );
   }//End Constructor
   
   private void configureDefaults(){
      showingPropertyFor( NutritionalUnit.Calories ).set( true );
      showingPropertyFor( NutritionalUnit.Carbohydrate ).set( true );
      showingPropertyFor( NutritionalUnit.Fat ).set( true );
      showingPropertyFor( NutritionalUnit.Protein ).set( true );
      showingPropertyFor( NutritionalUnit.Fibre ).set( true );
   }//End Method
   
   public ObjectProperty< Boolean > showingPropertyFor( NutritionalUnit unit ) {
      return showingUnitProperties.get( unit );
   }//End Method

   public NutsSettingsRegistrations registrations() {
      return registrations;
   }//End Method
   
}//End Class
