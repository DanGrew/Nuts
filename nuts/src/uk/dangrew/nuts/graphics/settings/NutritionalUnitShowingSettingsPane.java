package uk.dangrew.nuts.graphics.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveCheckBoxRegion;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutritionalUnitShowingSettingsPane extends GridPane {

   private final Map< ObjectProperty< Boolean >, CheckBox > checkBoxes;
   
   public NutritionalUnitShowingSettingsPane( NutsSettings settings ) {
      this.checkBoxes = new HashMap<>();
      
      new JavaFxStyle().configureConstraintsForEvenColumns( this, 1 );
      
      List< PropertyRowBuilder > builders = new ArrayList<>();
      TreeSet< NutritionalUnit > orderedUnits = new TreeSet<>( ( a, b ) -> a.name().compareToIgnoreCase( b.name() ) );
      orderedUnits.addAll( Arrays.asList( NutritionalUnit.values() ) );
      
      for ( NutritionalUnit unit : orderedUnits ) {
         builders.add( new PropertyRowBuilder()
            .withLabelName( unit.displayName() )
            .withBinding( checkBoxBinding( settings.showingPropertyFor( unit ) ) )
         );
      }
      this.add( new PropertiesPane( "Nutritional Units", builders ), 0, 0 );
   }//End Constructor
   
   private ResponsiveCheckBoxRegion checkBoxBinding( ObjectProperty< Boolean > property ){
      CheckBox box = new CheckBox();
      box.setPadding( new Insets( 5 ) );
      
      checkBoxes.put( property, box );
      
      return new ResponsiveCheckBoxRegion( 
               box, 
               property 
      );
   }//End Method
   
   CheckBox checkBoxFor( ObjectProperty< Boolean > unitProperty ) {
      return checkBoxes.get( unitProperty );
   }//End Method
   
}//End Class
