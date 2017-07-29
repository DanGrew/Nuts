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
                  .withProperty( tool.properties().portionDryWeight() ),
               new PropertyRowBuilder()
                  .withLabelName( "Cooked Weight Portion Size" )
                  .withProperty( tool.properties().portionCookedWeight() ),
               new PropertyRowBuilder()
                  .withLabelName( "Calories in Portion" )
                  .withProperty( tool.properties().portionCalories() ),
               new PropertyRowBuilder()
                  .withLabelName( "Dry to Cooked Scale Factor" )
                  .allowEditing( false )
                  .withProperty( tool.properties().dryToCookedScaleFactor() ),
               new PropertyRowBuilder()
                  .withLabelName( "Cooked to Dry Scale Factor" )
                  .allowEditing( false )
                  .withProperty( tool.properties().cookedToDryScaleFactor() )
      ) );
      
      getChildren().add( new PropertiesPane(
               "Dry Weight For Cooked Weight", 
               new PropertyRowBuilder()
                  .withLabelName( "Desired Cooked Weight" )
                  .withProperty( tool.properties().desiredCookedWeight() ),
               new PropertyRowBuilder()
                  .withLabelName( "Calculated Dry Weight" )
                  .allowEditing( false )
                  .withProperty( tool.properties().calculatedDryWeightForDesiredCookedWeight() )
      ) );
      
      getChildren().add( new PropertiesPane( 
               "Dry Weight For Calories", 
               new PropertyRowBuilder()
                  .withLabelName( "Desired Calories" )
                  .withProperty( tool.properties().desiredCalories() ),
               new PropertyRowBuilder()
                  .withLabelName( "Calculated Dry Weight" )
                  .allowEditing( false )
                  .withProperty( tool.properties().calculatedDryWeightForDesiredCalories() )
      ) );
   }//End Constructor
   
}//End Class
