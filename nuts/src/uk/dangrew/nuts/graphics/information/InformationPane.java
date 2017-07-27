/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.information;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import uk.dangrew.kode.friendly.javafx.FriendlyDesktop;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

/**
 * {@link InformationPane} provides some information about nutrition and the system for users.
 */
public class InformationPane extends BorderPane {
   
   private final FriendlyDesktop desktop;
   private final TextFlow flow;
   
   /**
    * Constructs a new {@link InformationPane}.
    */
   public InformationPane() {
      this.desktop = new FriendlyDesktop();
      setPadding( new Insets( 10.0 ) );
      
      this.flow = new TextFlow();
      GridPane wrapper = new GridPane();
      new JavaFxStyle().configureConstraintsForColumnPercentages( wrapper, 20, 60, 20 );
      wrapper.add( flow, 1, 0 );
      
      boldTextFor( "Introduction" );
      newLine();
      regularTextFor( 
               "Welcome! You're here to set some goals - but why? I guess you're objective is to lose "
               + "weight? Well, let's be more specific - noone wants to cut off a limb, and likewise noone wants to lose muscle. "
               + "The risk with 'losing weight' is we're so focussed on the number we're not concerned where it comes from. "
               + "We need to lose fat, not weight. But how do you lose fat? "
      );
      newLine();
      regularTextFor(
               "Simple, figure out how many calories you need a day, and create a deficit - eat less than you need!"
      );
      
      newLine();
      boldTextFor( "Equations" );
      newLine();
      regularTextFor(
               "To start with, we need to figure out how many calories we need a day to maintain our weight. There are some "
               + "guideline equations that allow you calculate this. I could go in to detail but wouldn't do the source I've "
               + "used justice, so have a look when you get 5 minutes if you want more information:" 
      );
      newLine();
      hyperlink( "http://www.myprotein.com/thezone/nutrition/how-to-clean-bulk-cut-diet/" );
      newLine();
      italicTextFor( 
               "There are lots of different websites providing calorie calculators, feel free to choose your own! None of them "
               + "are perfect, and none of them are built for you personally - you have to get a starting point, work with it "
               + "and adjust if it doesn't work for you." 
      );
      newLine();
      regularTextFor( "The basic concept is: calculate your BMR (basal metabolic rate) which is an estimate of how many calories "
               + "your body uses at rest, then multiply that by your PAL (physical activity level) which is a multiplier for your "
               + "BMR. This leaves you with your TEE (total energy expenditure). In simple terms, this is how many calories your "
               + "body burns and therefore how many you can eat to maintain your weight - maintenance calories." );
      newLine();
      
      boldTextFor( "Adjustments" );
      newLine();
      regularTextFor( "There are mixed opinions on exercise with calorie counting. How I like to figure it out is, if I exercise "
               + "and burn calories, those are extra calories in my daily allowance. Therefore, my daily allowance and plans "
               + "either goes up with the amount of exercise I do, or I create a deficit with those burnt calories. When you "
               + "configure your goals you can specify exercise which gets added to your TEE and you can set a deficit which "
               + "gets taken away from your TEE. Use these as you prefer - leaving them blank will ignore them." );
      newLine();
      
      boldTextFor( "Goals" );
      newLine();
      regularTextFor( "We have our calorie goal calculated, so what do we do now? Well, as I said before, eat less than you need. "
               + "That will get you going and you'll probably be losing some fat like me. I could make a speach about nutrition "
               + "and heathly eating but again, I won't do it justice - listen to the people who know:" );
      newLine();
      hyperlink( "https://www.youtube.com/user/VitruvianPhysique" );
      regularTextFor( " - This guy knows his stuff and gives you the information "
               + "you need to make your own decisions." );
      newLine();
      hyperlink( "https://www.youtube.com/user/JDCav24" );
      regularTextFor( " - You want to work out, build muscle, cut fat? This guy will show "
               + "you how, with or without a gym!" );
      newLine();
      regularTextFor( "Let's say you've gone through the videos and now you understand the importance of nutrition (or maybe you're "
               + "deciding to trust me - good choice!), instead of counting calories, we're actually going to count macros. Again, "
               + "listen to the experts not me, but in summary: 'Macros' = 'Macronutrients' = Carbohydrates, Fats and Protein. We're "
               + "going to figure out how many grams of each we should have a day. There are lots of sources of information about how to calculate "
               + "your macros and the generally accepted way - at least that I've found - is:" );
      newLine();
      boldTextFor( "Protein: " );
      regularTextFor( "Based on your weight, you need so much protein. For example, 1g per 1lb." );
      newLine();
      boldTextFor( "Fat: " );
      regularTextFor( "Based on your weight, you need so much fat. For example, 0.4g per 1lb." );
      newLine();
      boldTextFor( "Carbhydrates: " );
      regularTextFor( "Basically whatever is left in your calories after taking out protein and fat." );
      newLine();
      regularTextFor( "Well, how do we know how much protein and fat takes from our calories? Easy, roughly speaking (it does vary "
               + "from food to food and the different types each macronutrient) Carbohydrates = 4kcal per 1g, Fats = 9kcal per 1g, "
               + "Protein = 4kcal per 1g. So it's simple, carbohydrates (g) =  ( TTE - ( 4kcal x protein (g) + 9kcal x fats (g) ) ) / 4kcal. If "
               + "you haven't followed that don't worry, this application calculates it for you - either trust it or do some research." );
      newLine();
      
      boldTextFor( "Disclaimer: " );
      regularTextFor( "I'm not an expert, I've just watched some videos and summarized some information. This tool will not necessarily "
               + "work for you, or calculate your perfect macros - no system "
               + "can or will. This is an evolving process, you must monitor your macros and calories and adjust based on how your body "
               + "responds. On that note, some of the caclulations may not be right for you, some of the values may not be right for you "
               + "so I've left all values configurable. Don't like the TTE? Set your own and get on with it. Don't like eating that much "
               + "protein, or want more protein per pound? Change the amount per pound. I would very much recommend watching the videos "
               + "on the channels I've linked above, those guys are amazing." );
      newLine();
      
      ScrollPane scroller = new ScrollPane( wrapper );
      scroller.setFitToWidth( true );
      scroller.setBackground( new Background( new BackgroundFill( Color.TRANSPARENT, null, null ) ) );
      setCenter( scroller );
   }//End Constructor
   
   /**
    * Method to create and add a {@link Text} {@link javafx.scene.Node} to the {@link TextFlow} with a normal {@link Font}
    * for the given text. 
    * @param text the text to add.
    * @return the created {@link Text}.
    */
   private Text regularTextFor( String text ) {
      Text textNode = new Text( text );
      textNode.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
      flow.getChildren().add( textNode );
      return textNode;
   }//End Method
   
   /**
    * Method to create and add a {@link Text} {@link javafx.scene.Node} to the {@link TextFlow} with a bold {@link Font}
    * for the given text. 
    * @param text the text to add.
    * @return the created {@link Text}.
    */
   private Text boldTextFor( String text ) {
      Text textNode = regularTextFor( text );
      textNode.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
      return textNode;
   }//End Method
   
   /**
    * Method to create and add a {@link Text} {@link javafx.scene.Node} to the {@link TextFlow} with an italic {@link Font}
    * for the given text. 
    * @param text the text to add.
    * @return the created {@link Text}.
    */
   private Text italicTextFor( String text ) {
      Text textNode = regularTextFor( text );
      textNode.setFont(Font.font("Calibri", FontPosture.ITALIC, 14));
      return textNode;
   }//End Method
   
   /**
    * Method to create and add a {@link Text} {@link javafx.scene.Node} for a new line.
    */
   private void newLine(){
      regularTextFor( "\n\n" );
   }//End Method
   
   /**
    * Method to create and add a {@link Hyperlink} {@link javafx.scene.Node} to the {@link TextFlow} with a 
    * click launching {@link javafx.event.EventHandler}.
    * @param address the location to link to.
    */
   private void hyperlink( String address ) {
      Hyperlink hyperlink = new Hyperlink( address );
      hyperlink.setOnAction( e -> desktop.browseToIconWebsite( address ) );
      flow.getChildren().add( hyperlink );
   }//End Method

}//End Class
