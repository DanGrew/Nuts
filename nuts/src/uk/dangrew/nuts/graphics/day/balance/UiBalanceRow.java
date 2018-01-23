package uk.dangrew.nuts.graphics.day.balance;

import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.javafx.registrations.ChangeListenerMismatchBindingImpl;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.day.DayPlan;

public class UiBalanceRow extends GridPane {
   
   private final TextField dateLabel;
   private final TextField consumedLabel;
   private final TextField allowedLabel;
   private final TextField spentLabel;
   private final TextField balanceLabel;
   
   private final Button sync;
   private final Button reset;
   
   private final JavaFxStyle styling;
   private final Conversions conversions;
   private final DateTimeFormats dateTimeFormats;
   private final RegistrationManager registrations;
   private final UiBalanceController controller;
   
   public UiBalanceRow( UiBalanceController controller ) {
      this.controller = controller;
      this.registrations = new RegistrationManager();
      this.conversions = new Conversions();
      this.dateTimeFormats = new DateTimeFormats();
      this.styling = new JavaFxStyle();
      this.styling.configureConstraintsForColumnPercentages( this, 20, 15, 15, 15, 15, 10, 10 );
      this.styling.configureConstraintsForEvenRows( this, 1 );
      
      add( dateLabel = new TextField(), 0, 0 );
      add( consumedLabel = new TextField(), 1, 0 );
      add( allowedLabel = new TextField(), 2, 0 );
      add( spentLabel = new TextField(), 3, 0 );
      add( balanceLabel = new TextField(), 4, 0 );
      add( wrapButton( sync = new Button( "Sync" ) ), 5, 0 );
      add( wrapButton( reset = new Button( "Reset" ) ), 6, 0 );
      
      dateLabel.setEditable( false );
      consumedLabel.setEditable( true );
      allowedLabel.setEditable( true );
      spentLabel.setEditable( false );
      balanceLabel.setEditable( false );
      
      sync.setMaxWidth( Double.MAX_VALUE );
      reset.setMaxWidth( Double.MAX_VALUE );
      
   }//End Constructor
   
   private final Node wrapButton( Button button ) {
      return new BorderPane( button );
   }// End Method
   
   void setDayPlan( DayPlan plan ) {
      if ( plan == null ) {
         return;
      }
      
      sync.setOnAction( e -> controller.syncCalories( plan ) );
      reset.setOnAction( e -> controller.resetBalance( plan ) );
      
      registrations.shutdown();
      registrations.apply( new ChangeListenerMismatchBindingImpl<>( 
               plan.consumedCalories(), consumedLabel.textProperty(), 
               conversions.stringToDoubleFunction(), conversions.doubleToStringFunction()
      ) );
      
      registrations.apply( new ChangeListenerMismatchBindingImpl<>( 
               plan.allowedCalories(), allowedLabel.textProperty(), 
               conversions.stringToDoubleFunction(), conversions.doubleToStringFunction()
      ) );
      
      registrations.apply( new ChangeListenerMismatchBindingImpl<>( 
               plan.calorieBalance(), balanceLabel.textProperty(), 
               conversions.stringToDoubleFunction(), conversions.doubleToStringFunction()
      ) );
      
      double consumed = plan.consumedCalories().get();
      double allowed = plan.allowedCalories().get();
      double spent = consumed - allowed;
      double balance = plan.calorieBalance().get();
      
      dateLabel.setText( dateTimeFormats.nameNumberMonthYear( plan.date() ) );
      consumedLabel.setText( Double.toString( consumed ) );
      allowedLabel.setText( Double.toString( allowed ) );
      spentLabel.setText( Double.toString( spent ) );
      balanceLabel.setText( Double.toString( balance ) );
      
      if ( plan.date().isEqual( LocalDate.now() ) ) {
         dateLabel.setBackground( styling.backgroundFor( Color.LIGHTGREEN ) );
         consumedLabel.setBackground( styling.backgroundFor( Color.LIGHTGREEN ) );
         consumedLabel.setBorder( styling.borderFor( Color.BLUE, 1 ) );
         allowedLabel.setBackground( styling.backgroundFor( Color.LIGHTGREEN ) );
         allowedLabel.setBorder( styling.borderFor( Color.BLUE, 1 ) );
         spentLabel.setBackground( styling.backgroundFor( Color.LIGHTGREEN ) );
         balanceLabel.setBackground( styling.backgroundFor( Color.LIGHTGREEN ) );
      } else {
         dateLabel.setBackground( styling.backgroundFor( Color.WHITE ) );
         consumedLabel.setBackground( styling.backgroundFor( Color.WHITE ) );
         consumedLabel.setBorder( styling.borderFor( Color.BLUE, 1 ) );
         allowedLabel.setBackground( styling.backgroundFor( Color.WHITE ) );
         allowedLabel.setBorder( styling.borderFor( Color.BLUE, 1 ) );
         spentLabel.setBackground( styling.backgroundFor( Color.WHITE ) );
         balanceLabel.setBackground( styling.backgroundFor( Color.WHITE ) );
      }
   }//End Method

   TextField consumedCaloriesField() {
      return consumedLabel;
   }//End Method

   TextField allowedCaloriesField() {
      return allowedLabel;
   }//End Method

   TextField calorieBalanceField() {
      return balanceLabel;
   }//End Method

   Button syncButton() {
      return sync;
   }//End Method

   Button resetButton() {
      return reset;
   }//End Method
   
}//End Class
