/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.tools.dryweight;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import uk.dangrew.kode.javafx.custom.BoundTextProperty;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;

/**
 * The {@link DryWeightToolPane} provides a graphical interface for using the {@link DryWeightTool}.
 */
public class DryWeightToolPane extends VBox {
   
   /**
    * Constructs a new {@link DryWeightToolPane}.
    */
   public DryWeightToolPane() {
      DryWeightTool tool = new DryWeightTool();
      setPadding( new Insets( 10 ) );
      
      getChildren().add( new PropertiesPane( 
               "Portion Scaling",
               new PropertyRowBuilder()
                  .withLabelName( "Dry Weight Portion Size" )
                  .withBinding( new BoundTextProperty( tool.properties().portionDryWeight(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Cooked Weight Portion Size" )
                  .withBinding( new BoundTextProperty( tool.properties().portionCookedWeight(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Calories in Portion" )
                  .withBinding( new BoundTextProperty( tool.properties().portionCalories(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Dry to Cooked Scale Factor" )
                  .withBinding( new BoundTextProperty( tool.properties().dryToCookedScaleFactor(), false ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Cooked to Dry Scale Factor" )
                  .withBinding( new BoundTextProperty( tool.properties().cookedToDryScaleFactor(), false ) )
      ) );
      
      getChildren().add( new PropertiesPane(
               "Dry Weight For Cooked Weight", 
               new PropertyRowBuilder()
                  .withLabelName( "Desired Cooked Weight" )
                  .withBinding( new BoundTextProperty( tool.properties().desiredCookedWeight(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Calculated Dry Weight" )
                  .withBinding( new BoundTextProperty( tool.properties().calculatedDryWeightForDesiredCookedWeight(), false ) )
      ) );
      
      getChildren().add( new PropertiesPane( 
               "Dry Weight For Calories", 
               new PropertyRowBuilder()
                  .withLabelName( "Desired Calories" )
                  .withBinding( new BoundTextProperty( tool.properties().desiredCalories(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Calculated Dry Weight" )
                  .withBinding( new BoundTextProperty( tool.properties().calculatedDryWeightForDesiredCalories(), false ) )
      ) );
   }//End Constructor
   
}//End Class
