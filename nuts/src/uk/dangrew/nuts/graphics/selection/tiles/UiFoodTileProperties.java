package uk.dangrew.nuts.graphics.selection.tiles;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.number.NumberFormats;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class UiFoodTileProperties extends GridPane {
   
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
   
   private final Label calorieValue;
   private final PropertyTextSetter calorieValueSetter;
   
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
         label.setText( prefix + numberFormatter.twoDecimalPlace( value ) + suffix );
      }//End Method
      
   }//End Class
   
   public UiFoodTileProperties( Food food ) {
      this.food = food;
      this.styling = new JavaFxStyle();
      this.styling.configureConstraintsForEvenColumns( this, 3 );
      this.styling.configureConstraintsForEvenRows( this, 3 );
      
      this.add( this.carbsValue = createMacroLabel( Color.SKYBLUE ), 0, 0 );
      this.add( this.fatsValue = createMacroLabel( Color.ORANGE ), 1, 0 );
      this.add( this.proteinValue = createMacroLabel( Color.LIGHTGREEN ), 2, 0 );
      
      this.add( this.carbsRatio = createMacroLabel( Color.SKYBLUE ), 0, 1 );
      this.add( this.fatsRatio = createMacroLabel( Color.ORANGE ), 1, 1 );
      this.add( this.proteinRatio = createMacroLabel( Color.LIGHTGREEN ), 2, 1 );
      
      this.add( this.calorieValue = createMacroLabel( Color.PINK ), 0, 2 );
      GridPane.setColumnSpan( calorieValue, 3 );
      
      this.carbsValueSetter = new PropertyTextSetter( carbsValue, NutritionalUnit.Carbohydrate.of( food ).property(), "C: ", "g" );
      this.carbsRatioSetter = new PropertyTextSetter( carbsRatio, NutritionalUnit.Carbohydrate.of( food.foodAnalytics() ).property(), "C: ", "%" );
      this.fatsValueSetter = new PropertyTextSetter( fatsValue, NutritionalUnit.Fat.of( food ).property(), "F: ", "g" );
      this.fatsRatioSetter = new PropertyTextSetter( fatsRatio, NutritionalUnit.Fat.of( food.foodAnalytics() ).property(), "F: ", "%" );
      this.proteinValueSetter = new PropertyTextSetter( proteinValue, NutritionalUnit.Protein.of( food ).property(), "P: ", "g" );
      this.proteinRatioSetter = new PropertyTextSetter( proteinRatio, NutritionalUnit.Protein.of( food.foodAnalytics() ).property(), "P: ", "%" );
      
      this.calorieValueSetter = new PropertyTextSetter( calorieValue, NutritionalUnit.Calories.of( food ).property(), "", "kcal" );
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
      NutritionalUnit.Carbohydrate.of( food ).property().removeListener( carbsValueSetter );
      NutritionalUnit.Fat.of( food ).property().removeListener( fatsValueSetter );
      NutritionalUnit.Protein.of( food ).property().removeListener( proteinValueSetter );
      NutritionalUnit.Calories.of( food ).property().removeListener( calorieValueSetter );
      
      NutritionalUnit.Carbohydrate.of( food.foodAnalytics() ).property().removeListener( carbsRatioSetter );
      NutritionalUnit.Fat.of( food.foodAnalytics() ).property().removeListener( fatsRatioSetter );
      NutritionalUnit.Protein.of( food.foodAnalytics() ).property().removeListener( proteinRatioSetter );
   }//End Method

   Label valueLabelFor( NutritionalUnit macro ) {
      switch ( macro ) {
         case Carbohydrate:
            return carbsValue;
         case Fat:
            return fatsValue;
         case Protein:
            return proteinValue;
         default:
            return null;
      }
   }//End Method

   Label ratioLabelFor( NutritionalUnit macro ) {
      switch ( macro ) {
         case Carbohydrate:
            return carbsRatio;
         case Fat:
            return fatsRatio;
         case Protein:
            return proteinRatio;
         default:
            return null;
      }
   }//End Method

   Labeled calorieValueLabel() {
      return calorieValue;
   }//End Method

}//End Class
