package uk.dangrew.nuts.graphics.selection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.number.NumberFormats;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class UiFoodTileMacros extends GridPane {
   
   private final JavaFxStyle styling;
   
   private final Food food;
   
   private final Label carbsValue;
   private final Label carbsRatio;
   private final PropertyTextSetter carbsValueSetter;
   private final PropertyTextSetter carbsRatioSetter;
   
   private final Label fatsValue;
   private final Label fatsRatio;
   private final PropertyTextSetter fatsValueSetter;
   private final PropertyTextSetter fatsRatioSetter;
   
   private final Label proteinValue;
   private final Label proteinRatio;
   private final PropertyTextSetter proteinValueSetter;
   private final PropertyTextSetter proteinRatioSetter;
   
   private static class PropertyTextSetter implements ChangeListener< Double > {
      
      private final NumberFormats numberFormatter;
      private final Label label;
      private final String prefix;
      private final String suffix;
      
      public PropertyTextSetter( Label label, ObjectProperty< Double > property, String prefix, String suffix ) {
         this.label = label;
         this.prefix = prefix;
         this.suffix = suffix;
         this.numberFormatter = new NumberFormats();
         
         property.addListener( this );
         update( property.get() );
      }//End Constructor

      @Override public void changed( ObservableValue< ? extends Double > observable, Double oldValue, Double newValue ) {
         update( newValue );
      }//End Constructor
      
      private void update( double value ) {
         label.setText( prefix + " " + numberFormatter.twoDecimalPlace( value ) + suffix );
      }//End Method
      
   }//End Class
   
   public UiFoodTileMacros( Food food ) {
      this.food = food;
      this.styling = new JavaFxStyle();
      this.styling.configureConstraintsForEvenColumns( this, 3 );
      this.styling.configureConstraintsForEvenRows( this, 2 );
      
      this.add( this.carbsValue = createMacroLabel( Color.SKYBLUE ), 0, 0 );
      this.add( this.fatsValue = createMacroLabel( Color.ORANGE ), 1, 0 );
      this.add( this.proteinValue = createMacroLabel( Color.LIGHTGREEN ), 2, 0 );
      
      this.add( this.carbsRatio = createMacroLabel( Color.SKYBLUE ), 0, 1 );
      this.add( this.fatsRatio = createMacroLabel( Color.ORANGE ), 1, 1 );
      this.add( this.proteinRatio = createMacroLabel( Color.LIGHTGREEN ), 2, 1 );
      
      this.carbsValueSetter = new PropertyTextSetter( carbsValue, food.properties().carbohydrates(), "C:", "g" );
      this.carbsRatioSetter = new PropertyTextSetter( carbsRatio, food.foodAnalytics().carbohydratesRatioProperty(), "C:", "%" );
      this.fatsValueSetter = new PropertyTextSetter( fatsValue, food.properties().fats(), "F:", "g" );
      this.fatsRatioSetter = new PropertyTextSetter( fatsRatio, food.foodAnalytics().fatsRatioProperty(), "F:", "%" );
      this.proteinValueSetter = new PropertyTextSetter( proteinValue, food.properties().protein(), "P:", "g" );
      this.proteinRatioSetter = new PropertyTextSetter( proteinRatio, food.foodAnalytics().proteinRatioProperty(), "P:", "%" );
   }//End Constructor
   
   private Label createMacroLabel( Color background ) {
      Label label = styling.createBoldLabel( "" );
      label.setBackground( styling.backgroundFor( background ) );
      label.setBorder( styling.borderFor( Color.BLACK, 2 ) );
      label.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
      label.setAlignment( Pos.CENTER );
      return label;
   }//End Method

   public void detach() {
      food.properties().carbohydrates().removeListener( carbsValueSetter );
      food.properties().fats().removeListener( fatsValueSetter );
      food.properties().protein().removeListener( proteinValueSetter );
      
      food.foodAnalytics().carbohydratesRatioProperty().removeListener( carbsRatioSetter );
      food.foodAnalytics().fatsRatioProperty().removeListener( fatsRatioSetter );
      food.foodAnalytics().proteinRatioProperty().removeListener( proteinRatioSetter );
   }//End Method

   Label valueLabelFor( MacroNutrient macro ) {
      switch ( macro ) {
         case Carbohydrates:
            return carbsValue;
         case Fats:
            return fatsValue;
         case Protein:
            return proteinValue;
      }
      return null;
   }//End Method

   Label ratioLabelFor( MacroNutrient macro ) {
      switch ( macro ) {
         case Carbohydrates:
            return carbsRatio;
         case Fats:
            return fatsRatio;
         case Protein:
            return proteinRatio;
      }
      return null;
   }//End Method

}//End Class
