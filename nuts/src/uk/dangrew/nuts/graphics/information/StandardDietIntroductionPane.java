/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.information;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;

public class StandardDietIntroductionPane extends BaseInformationPane {
   
   private static final Color ERROR_COLOUR = Color.RED;
   private static final Color COMMENT_COLOUR = Color.CORNFLOWERBLUE;

   @Override protected void populateText( VBox textWrapper ) {
      setBorder( new JavaFxStyle().borderFor( ERROR_COLOUR ) );
      
      textWrapper.getChildren().add( 
            new TextFlowBuilder()
               .withColour( ERROR_COLOUR ).withSize( 18 ).bold( "Original Introduction" )
               .newLine().newLine()
               .resetFontSize()
               .normal( "Welcome! You're here to set some goals - but why? I guess your objective is to lose "
                        + "weight? Well, let's be more specific - noone wants to cut off a limb, and likewise noone wants to lose muscle. "
                        + "The risk with 'losing weight' is we're so focussed on the number we're not concerned where it comes from. "
                        + "We need to lose fat, not weight. But how do you lose fat? " )
               .newLine().newLine()
               .normal( "Simple, figure out how many calories you need a day, and create a deficit - eat less than you need!" )
               .newLine()
               .withColour( COMMENT_COLOUR )
               .bold( "Ahhhh, how wrong I was. This lead me down a dark path - hormones, not calories, insulin is the key." )
               .newLine().newLine()
               .build() 
      );
      
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withColour( ERROR_COLOUR ).withSize( 18 ).bold( "Equations" )
                  .newLine().newLine()
                  .resetFontSize()
                  .normal( "To start with, we need to figure out how many calories we need a day to maintain our weight. There are some "
                           + "guideline equations that allow you calculate this. I could go in to detail but wouldn't do the source I've "
                           + "used justice, so have a look when you get 5 minutes if you want more information:" )
                  .newLine().newLine()
                  .withHyperlink( "http://www.myprotein.com/thezone/nutrition/how-to-clean-bulk-cut-diet/", desktop() )
                  .newLine().newLine()
                  .italic( "There are lots of different websites providing calorie calculators, feel free to choose your own! None of them "
                           + "are perfect, and none of them are built for you personally - you have to get a starting point, work with it "
                           + "and adjust if it doesn't work for you." )
                  .newLine().newLine()
                  .normal( "The basic concept is: calculate your BMR (basal metabolic rate) which is an estimate of how many calories "
                           + "your body uses at rest, then multiply that by your PAL (physical activity level) which is a multiplier for your "
                           + "BMR. This leaves you with your TEE (total energy expenditure). In simple terms, this is how many calories your "
                           + "body burns and therefore how many you can eat to maintain your weight - maintenance calories." )
                  .newLine()
                  .withColour( COMMENT_COLOUR )
                  .bold( "The problem here is that we don't want to fiddle our BMR, we don't want to lower our metabolism, and simply "
                           + "restricting what we give it will do just that! Your body adapts to everything... everything... eating less, "
                           + "training more, lifting more, doing the same exercises/reps, everything. You want improvement, you can't "
                           + "simply create a formula and follow it." )
                  .newLine().newLine()
                  .build()
      );
      
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withColour( ERROR_COLOUR ).withSize( 18 ).bold( "Adjustments" )
                  .newLine().newLine()
                  .resetFontSize()
                  .normal( "There are mixed opinions on exercise with calorie counting. How I like to figure it out is, if I exercise "
                           + "and burn calories, those are extra calories in my daily allowance. Therefore, my daily allowance and plans "
                           + "either goes up with the amount of exercise I do, or I create a deficit with those burnt calories. When you "
                           + "configure your goals you can specify exercise which gets added to your TEE and you can set a deficit which "
                           + "gets taken away from your TEE. Use these as you prefer - leaving them blank will ignore them." )
                  .newLine()
                  .withColour( COMMENT_COLOUR )
                  .bold( "Oh gosh, another kick in the teeth - and no! no calories burnt for that kick! - Eating back calories "
                           + "only works so far, eventually you get to a point where it's either not accurate with your fitness level "
                           + "and metabolism, or you form an obsession with exercising in order to overeat... or both! Again, your "
                           + "body will adapt. Run 10k after never doing it, yeah you might get 1000kcal (that's what I counted) "
                           + "but keep doing it and your body will adapt and become efficient, you won't burn 1000kcal in future. "
                           + "Now you're just overeating, but that's ok FitBit says I burnt 1000kcal! (sorry, FitBit, I'm actually "
                           + "a big fan but people need to understand their bodies and that technology is an approximation." )
                  .newLine().newLine()
                  .build()
      );
      
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withColour( ERROR_COLOUR ).withSize( 18 ).bold( "Goals" )
                  .newLine().newLine()
                  .resetFontSize()
                  .normal( "We have our calorie goal calculated, so what do we do now? Well, as I said before, eat less than you need. "
                           + "That will get you going and you'll probably be losing some fat like me. I could make a speach about nutrition "
                           + "and heathly eating but again, I won't do it justice - listen to the people who know:" )
                  .newLine().newLine()
                  .withHyperlink( "https://www.youtube.com/user/VitruvianPhysique", desktop() )
                  .normal( " - This guy knows his stuff and gives you the information "
                           + "you need to make your own decisions." )
                  .newLine().newLine()
                  .withHyperlink( "https://www.youtube.com/user/JDCav24", desktop() )
                  .normal( " - You want to work out, build muscle, cut fat? This guy will show "
                           + "you how, with or without a gym!" )
                  .newLine().newLine()
                  .normal( "Let's say you've gone through the videos and now you understand the importance of nutrition (or maybe you're "
                           + "deciding to trust me - good choice!), instead of counting calories, we're actually going to count macros. Again, "
                           + "listen to the experts not me, but in summary: 'Macros' = 'Macronutrients' = Carbohydrates, Fats and Protein. We're "
                           + "going to figure out how many grams of each we should have a day. There are lots of sources of information about how to calculate "
                           + "your macros and the generally accepted way - at least that I've found - is:" )
                  .newLine().newLine()
                  .bold( "Protein: " )
                  .normal( "Based on your weight, you need so much protein. For example, 1g per 1lb." )
                  .newLine().newLine()
                  .bold( "Fat: " )
                  .normal( "Based on your weight, you need so much fat. For example, 0.4g per 1lb." )
                  .newLine().newLine()
                  .bold( "Carbhydrates: " )
                  .normal( "Basically whatever is left in your calories after taking out protein and fat." )
                  .newLine().newLine()
                  .normal( "Well, how do we know how much protein and fat takes from our calories? Easy, roughly speaking (it does vary "
                           + "from food to food and the different types each macronutrient) Carbohydrates = 4kcal per 1g, Fats = 9kcal per 1g, "
                           + "Protein = 4kcal per 1g. So it's simple, carbohydrates (g) =  ( TTE - ( 4kcal x protein (g) + 9kcal x fats (g) ) ) / 4kcal. If "
                           + "you haven't followed that don't worry, this application calculates it for you - either trust it or do some research." )
                  .newLine()
                  .withColour( COMMENT_COLOUR )
                  .bold( "Yeah, well, this isn't all bad really... the calculations are reasonable, I stil count macros, just with different "
                           + "targets. Personally, not a fan of high carbohydrates any more, but that's up to you. Lots of successful "
                           + "people eating high carbohydrates (but I bet not cookies and belvitas - evil things!)." )
                  .newLine().newLine()
                  .build()
      );
      
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withColour( ERROR_COLOUR )
                  .bold( "Disclaimer: " )
                  .normal( "I'm not an expert, I've just watched some videos and summarized some information. This tool will not necessarily "
                           + "work for you, or calculate your perfect macros - no system "
                           + "can or will. This is an evolving process, you must monitor your macros and calories and adjust based on how your body "
                           + "responds. On that note, some of the caclulations may not be right for you, some of the values may not be right for you "
                           + "so I've left all values configurable. Don't like the TTE? Set your own and get on with it. Don't like eating that much "
                           + "protein, or want more protein per pound? Change the amount per pound. I would very much recommend watching the videos "
                           + "on the channels I've linked above, those guys are amazing." )
                  .build()
      );
   }//End Method
   
}//End Class
