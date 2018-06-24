package uk.dangrew.nuts.graphics.tutorial.database.tutorials;

import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.event.Event;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.tutorial.architecture.manipulation.DatabaseManipulator;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialSelector;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseComponents;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTableTutorial;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTutorials;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class DttMakingMeals extends DatabaseTableTutorial {

   private static final String UNNAMED = "Unnamed";
   private static final String OMELETTE = "Omelette";
   private static final String BACON_OMELETTE = "Bacon Omelette";
   private static final String BACON = "Bacon (100g)";
   private static final String CHEESE = "Cheese (100g)";
   private static final String CREAM = "Cream (Heavy) (30ml)";
   private static final String EGG = "Egg (Large)";
   
   public DttMakingMeals( DatabaseComponents components, TutorialGlass glass, TutorialSelector selector ) {
      super( components, glass, selector );
   }//End Constructor
   
   @Override public List< Runnable > defineInstructions() {
      return Arrays.asList(  
               this::introduceDatabase,
               this::prepareDatabaseForTutorial,
               this::populateDatabaseWithSampleItems,
               this::describeAddButtonUsage,
               this::triggerAddMeal,
               this::showAddedMealInTable,
               this::renameMeal,
               this::introduceMealTable,
               this::introduceAddPortion,
               this::addBasicIngredientsToMeal,
               this::highlightPortionsAdded,
               this::highlightPortionColumn,
               this::changePortions,
               this::highlightIngredientTotals,
               this::highlightMealTotals,
               this::introduceCopyButton,
               this::copyPortion,
               this::showResultOfCopy,
               this::introduceRemove,
               this::removePortion,
               this::removeResultAndNavigation,
               this::introduceUpButton,
               this::movePortionUp,
               this::introduceDownButton,
               this::movePortionDown,
               this::introduceMealsWithinMeals,
               this::createMealWithinMeal,
               this::completeTutorial
      );
   }//End Constructor
   
   private void introduceDatabase(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage( new TextFlowBuilder()
                        .withFlowPadding( 10 )
                        .normal( "Welcome to the Nuts Database Manager!" )
                        .build()
                )
               .withRespectTo( components().parent() )
               .pointing( ArrowLocation.BOTTOM_CENTER )
      );
   }//End Method
   
   private void prepareDatabaseForTutorial(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage(  new TextFlowBuilder()
                        .withFlowPadding( 10 )
                        .normal( "Hopefully by now you know how to add food items to the database and configure their " ).newLine()
                        .normal( "properties. If not, I'd recommend following the previous tutorial (" )
                           .normal( DatabaseTutorials.AddingFoodItemToTable.tutorialId() )
                        .normal( "). " ).newLine()
                        .normal( "For this tutorial, we will be creating meals using items from the database. I'm going to go " ).newLine()
                        .normal( "ahead and create some example items for us to work with." )
                        .build()
               )
               .focussingOn( components().mainTable() )
      );
   }//End Method
   
   private void populateDatabaseWithSampleItems() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                        FoodItem egg = components().database().foodItems().createConcept( EGG );
                        NutritionalUnit.Calories.of( egg ).set( 70.0 );
                        egg.nutrition().setMacroNutrients( 0.35, 6.3, 8.82 );
                     
                        FoodItem cream = components().database().foodItems().createConcept( CREAM );
                        NutritionalUnit.Calories.of( cream ).set( 140.0 );
                        cream.nutrition().setMacroNutrients( 0.48, 15.15, 0.45 );
                        
                        FoodItem cheese = components().database().foodItems().createConcept( CHEESE );
                        NutritionalUnit.Calories.of( cheese ).set( 416.0 );
                        cheese.nutrition().setMacroNutrients( 0.1, 34.9, 25.4 );
                        
                        FoodItem bacon = components().database().foodItems().createConcept( BACON );
                        NutritionalUnit.Calories.of( bacon ).set( 274.9 );
                        bacon.nutrition().setMacroNutrients( 1.4, 22.5, 16.7 );
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void describeAddButtonUsage(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage(  new TextFlowBuilder()
                        .withFlowPadding( 10 )
                        .normal( "A meal is made up of other food items and meals, with specific portions of each. " ).newLine()
                        .normal( "Let's create one! We just need to click the add button and select 'Meals' from the " ).newLine()
                        .normal( "popup dialog. I'll do that for you." ).newLine()
                        .build()
               )
               .focussingOn( components().mainTableAddButton() )
      );
   }//End Method
   
   private void triggerAddMeal() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                      components().mainTableFoodTypeDialogManipulator()
                         .disableInput()
                         .select( FoodTypes.Meals );
                      components().mainTableAddButton().fire();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableFoodTypeDialog().close();
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void showAddedMealInTable(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "We now have a meal that we can edit. Let's rename it first." )
                           .build()
                  )
                  .focussingOn( components().mainTableComponents().findRow( UNNAMED ).node() )
      );
   }//End Method
   
   private void renameMeal() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                      components().database().meals().objectList().get( 0 ).properties().nameProperty().set( "Omelette" );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                      components().mainTableComponents().findRow( OMELETTE ).selectInTable();;
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void introduceMealTable(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Whenever a meal is selected in the database table, this table shows the contents of " ).newLine()
                           .normal( "that meal. There's nothing in the meal at the moment, so there is nothing in the table. " ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTable() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void introduceAddPortion(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "We can add foods from the database to the meal. Here, the add button will launch the food " ).newLine()
                           .normal( "selection dialog. This dialog provides searching, filtering and multi-select to make the " ).newLine()
                           .normal( "creation of meals much quicker. More on that in later tutorials though... For now, I'll " ).newLine()
                           .normal( "just add the ingredients to the meal for you." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableAddButton() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void addBasicIngredientsToMeal() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                      DatabaseManipulator manipulator = components().databaseManipulator();
                      Meal omelette = manipulator.find( OMELETTE, Database::meals );
                      omelette.portions().add( new FoodPortion( manipulator.find( EGG, Database::foodItems ), 100.0 ) );
                      omelette.portions().add( new FoodPortion( manipulator.find( CREAM, Database::foodItems ), 100.0 ) );
                      omelette.portions().add( new FoodPortion( manipulator.find( CHEESE, Database::foodItems ), 100.0 ) );
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void highlightPortionsAdded(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Now we can see that the meal has some ingredients!" ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTable() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void highlightPortionColumn(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Notice here that there is a new column. This represents the portion of the ingredient used. " ).newLine()
                           .normal( "In this case, I've added 100% (one portion) of each food. That won't really make a good omelette " ).newLine()
                           .normal( "though. You can double click on the portion and enter a new value. Note that this portion is " ).newLine()
                           .normal( "the % of the ingredient, so 3 eggs would be 300%. Cream has been added in as 30ml, so if we " ).newLine()
                           .normal( "want 60ml, the portion would be 200%. I'll go ahead and change that for you (and maybe 100g " ).newLine()
                           .normal( "of cheese is a bit much!)" ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableComponents().row( 0 ).cell( 1 ) )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void changePortions() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( EGG )
                        .triggerCellEdit( 1 )
                        .concept().setPortion( 300 );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( CREAM )
                        .triggerCellEdit( 1 )
                        .concept().setPortion( 200 );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( CHEESE )
                        .triggerCellEdit( 1 )
                        .concept().setPortion( 40 );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().finishCellEdit();
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void highlightIngredientTotals(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Notice that when I updated the portions of the ingredients, the properties of the ingredients " ).newLine()
                           .normal( "changed. Each property is scaled based on the portion used making it much easier to count " ).newLine()
                           .normal( "calories and macros." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTable() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void highlightMealTotals(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Now if we look in the database at the meal, it has the same properties. The values " ).newLine()
                           .normal( "associated with the meal are the totals for each property, summing each ingredients value " ).newLine()
                           .normal( "accounting for it's portion. This is very powerful for building up the nutritional information " ).newLine()
                           .normal( "entire meals." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mainTableComponents().findRow( OMELETTE ).node() )
      );
   }//End Method
   
   private void introduceCopyButton(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "The meal table has some controls to make the creation of a meal a little easier. This " ).newLine()
                           .normal( "is the copy button. It simply duplicates the currently selected portion. This can be " ).newLine()
                           .normal( "useful if there is a logical breakdown to the meal, maybe some cheese for the filling and " ).newLine()
                           .normal( "some more for the topping. You just select a row and click copy, I'll show you." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableCopyButton() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void copyPortion() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( CHEESE ).selectInTable();
                     components().mealTableCopyButton().fire();
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void showResultOfCopy(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "There you go! Another bit of cheese for our omelette. " ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableComponents().findRow( CHEESE, 2 ).node() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void introduceRemove(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Another control we have is the remove button, which will simply remove the " ).newLine()
                           .normal( "selected portion from the meal. I'll show you by removing the cheese we just added." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableRemoveButton() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void removePortion() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( CHEESE, 2 ).selectInTable();;
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableRemoveButton().fire();
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void removeResultAndNavigation(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "There you go, back to where we were. Finally, navigation buttons are provided purely " ).newLine()
                           .normal( "to allow ingredients to be ordered however you'd like them be. It can be helpful " ).newLine()
                           .normal( "to show the breakdown of the ingredients, such as all of the filling then all of the " ).newLine()
                           .normal( "topping. I'll go ahead and use these buttons to rearrange the meal a little." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableComponents().findRow( CHEESE ).node() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void introduceUpButton(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Move all the way to the top..." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableUpButton() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void movePortionUp() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( CHEESE ).selectInTable();
                     components().mealTableUpButton().fire();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableUpButton().fire();
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void introduceDownButton(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Move back to the bottom..." ).newLine()
                           .build()
                  )
                  .focussingOn( components().mealTableDownButton() )
                  .pointing( ArrowLocation.RIGHT_CENTER )
      );
   }//End Method
   
   private void movePortionDown() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableDownButton().fire();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableDownButton().fire();
                  } )
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void introduceMealsWithinMeals(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
                           .normal( "Now, we can start to get clever by nesting meals within meals to make bigger meals, " ).newLine()
                           .normal( "plans and recipes. You know everything needed to do this, but just to be sure, I'll " ).newLine()
                           .normal( "show you how to do this as a series of steps... sit back and enjoy!" ).newLine()
                           .build()
                  )
                  .focussingOn( components().mainTable() )
      );
   }//End Method
   
   private void createMealWithinMeal() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                      glass().tutorUser( new TutorMessageBuilder().highlighting( components().mainTableAddButton() ) );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                      components().mainTableFoodTypeDialogManipulator()
                         .disableInput()
                         .select( FoodTypes.Meals );
                      components().mainTableAddButton().fire();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableFoodTypeDialog().close();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableComponents().findRow( UNNAMED ).selectInTable();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableComponents().findRow( UNNAMED ).concept().properties().nameProperty().set( BACON_OMELETTE );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     glass().tutorUser( new TutorMessageBuilder().highlighting( components().mainTableComponents().findRow( BACON_OMELETTE ).node() ) );
                     components().mainTableComponents().findRow( BACON_OMELETTE ).selectInTable();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     glass().tutorUser( new TutorMessageBuilder().highlighting( components().mealTableAddButton() ) );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     Meal baconOmelette = components().databaseManipulator().find( BACON_OMELETTE, Database::meals );
                     Meal omelette = components().databaseManipulator().find( OMELETTE, Database::meals );
                     FoodItem bacon = components().databaseManipulator().find( BACON, Database::foodItems );
                     baconOmelette.portions().add( new FoodPortion( omelette, 100 ) );
                     baconOmelette.portions().add( new FoodPortion( bacon, 100 ) );
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( BACON )
                        .selectInTable()
                        .triggerCellEdit( 1 );
                  } ) 
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().findRow( BACON ).concept().setPortion( 150 );
                  } ) 
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mealTableComponents().finishCellEdit();
                  } ) 
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableComponents().findRow( BACON_OMELETTE ).selectInTable();
                  } ) 
                  .pauseFor( 1 )
      );
   }//End Method
   
   private void completeTutorial(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage( new TextFlowBuilder()
                        .withFlowPadding( 10 )
                        .normal( "That completes the tutorial! You are now fully trained in creating Meals. " ).newLine()
                        .normal( "Go check out some of the other tutorials to become a Nuts master! See you soon!" )
                        .build()
                )
               .withRespectTo( components().parent() )
               .pointing( ArrowLocation.BOTTOM_CENTER )
      );
   }//End Method
   
}//End Class
