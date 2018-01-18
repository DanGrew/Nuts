package uk.dangrew.nuts.goal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;

public class DerivedGoal implements Goal {
   
   private final FoodProperties properties;
   private final FoodAnalytics analytics;
   private final ObjectProperty< Double > calorieOffset;
   private final ObjectProperty< Double > calorieDeficit;
   
   private Goal baseGoal;
   
   public DerivedGoal( String name ) {
      this( new FoodProperties( name ), new GoalAnalytics(), new MacroRatioCalculator() );
   }//End Constructor
   
   DerivedGoal( FoodProperties properties, FoodAnalytics analytics, MacroRatioCalculator macroRatioCalc ) {
      this.properties = properties;
      this.analytics = analytics;
      this.calorieOffset = new SimpleObjectProperty<>( 0.0 );
      this.calorieDeficit = new SimpleObjectProperty<>( 0.0 );
      macroRatioCalc.associate( properties, analytics );
   }//End Constructor
   
   public void setBaseGoal( Goal baseGoal ) {
      if ( this.baseGoal != null ) {
         throw new IllegalStateException( "Cannot change base goal." );
      }
      this.baseGoal = baseGoal;
      new MacroGoalCalculator().associate( this );
      new CalorieGoalCalculator().associate( this );
      
      ChangeListener< Double > calorieCalculator = ( s, o, n ) -> recalculateCalories();
      baseGoal.calorieDeficit().addListener( calorieCalculator );
      calorieDeficit().addListener( calorieCalculator );
      calorieOffset().addListener( calorieCalculator );
   }//End Method
   
   private void recalculateCalories(){
      calorieDeficit.set( baseGoal.calorieDeficit().get() - calorieOffset.get() );
   }//End Method
   
   public Goal baseGoal() {
      return baseGoal;
   }//End Method
   
   public ObjectProperty< Double > calorieOffset(){
      return calorieOffset;
   }//End Method
   
   @Override public ObjectProperty< Double > calorieDeficit() {
      return calorieDeficit;
   }//End Method
   
   @Override public FoodProperties properties() {
      return properties;
   }//End Method

   @Override public FoodAnalytics foodAnalytics() {
      return analytics;
   }//End Method

   @Override public DerivedGoal duplicate( String referenceId ) {
      DerivedGoal duplicate = new DerivedGoal( properties().nameProperty().get() + referenceId );
      if ( baseGoal != null ) {
         duplicate.setBaseGoal( baseGoal );
      }
      duplicate.calorieOffset.set( calorieOffset.get() );
      return duplicate;
   }//End Method

   @Override public ObjectProperty< Double > age() {
      return baseGoal.age();
   }//End Method

   @Override public ObjectProperty< Double > weight() {
      return baseGoal.weight();
   }//End Method

   @Override public ObjectProperty< Double > height() {
      return baseGoal.height();
   }//End Method

   @Override public ObjectProperty< Double > bmr() {
      return baseGoal.bmr();
   }//End Method

   @Override public ObjectProperty< Double > pal() {
      return baseGoal.pal();
   }//End Method

   @Override public ObjectProperty< Double > tee() {
      return baseGoal.tee();
   }//End Method

   @Override public ObjectProperty< Double > exerciseCalories() {
      return baseGoal.exerciseCalories();
   }//End Method

   @Override public ObjectProperty< Double > proteinPerPound() {
      return baseGoal.proteinPerPound();
   }//End Method

   @Override public ObjectProperty< Double > fatPerPound() {
      return baseGoal.fatPerPound();
   }//End Method

   @Override public ObjectProperty< Gender > gender() {
      return baseGoal.gender();
   }//End Method

}//End Class
