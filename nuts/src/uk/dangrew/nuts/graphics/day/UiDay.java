/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.day.DayPlan;

public class UiDay extends GridPane {

   private final JavaFxStyle styling;
   private final DayPlan dayPlan;
   
   public UiDay( DayPlan dayPlan ) {
      this.dayPlan = dayPlan;
      this.styling = new JavaFxStyle();
      
      add( new Label( dayPlan.date().toString() ), 0, 0 );
      setMaxWidth( Double.MAX_VALUE );
      setMaxHeight( Double.MAX_VALUE );
      
      setPadding( new Insets( 10 ) );
      
      Rectangle shape = new Rectangle( 20, 20 );
      shape.setArcWidth( 5 );
      shape.setArcHeight( 5 );
      setShape( shape );
      setBorder( new Border( new BorderStroke( Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths( 4 ) ) ) );
      
      deselect();
   }//End Constructor
   
   public DayPlan association() {
      return dayPlan;
   }//End Method

   public void select() {
      setBackground( styling.backgroundFor( Color.ORANGE ) );
   }//End Method

   public void deselect() {
      int month = LocalDate.now().getMonthValue();
      if ( dayPlan.date().equals( LocalDate.now() ) ) {
         setBackground( styling.backgroundFor( Color.LIGHTGREEN ) );
      } else if ( dayPlan.date().getMonthValue() == month ) {
         setBackground( styling.backgroundFor( Color.LIGHTBLUE ) );
      } else {
         setBackground( styling.backgroundFor( Color.LIGHTGRAY ) );
      }
   }//End Method
   
}//End Class
