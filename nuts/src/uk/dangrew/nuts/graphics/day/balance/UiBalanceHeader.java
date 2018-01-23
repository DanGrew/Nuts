package uk.dangrew.nuts.graphics.day.balance;


import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class UiBalanceHeader extends GridPane {
   
   private final Label dateLabel;
   private final Label consumedLabel;
   private final Label allowedLabel;
   private final Label spentLabel;
   private final Label balanceLabel;
   
   public UiBalanceHeader() {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForColumnPercentages( this, 20, 15, 15, 15, 15, 10, 10 );
      
      add( dateLabel = styling.createBoldLabel( "Day" ), 0, 0 );
      add( consumedLabel = styling.createBoldLabel( "Consumed (kcal)" ), 1, 0 );
      add( allowedLabel = styling.createBoldLabel( "Allowed (kcal)" ), 2, 0 );
      add( spentLabel = styling.createBoldLabel( "Spent (kcal)" ), 3, 0 );
      add( balanceLabel = styling.createBoldLabel( "Balance (kcal)" ), 4, 0 );
   }//End Constructor
   
}//End Class
