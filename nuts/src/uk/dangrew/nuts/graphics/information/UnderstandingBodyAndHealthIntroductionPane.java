package uk.dangrew.nuts.graphics.information;

import javafx.scene.layout.VBox;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;

public class UnderstandingBodyAndHealthIntroductionPane extends BaseInformationPane {

   @Override protected void populateText( VBox textWrapper ) {
      textWrapper.getChildren().add( 
            new TextFlowBuilder()
               .withSize( 18 ).bold( "A New Introduction! 31/05/18" )
               .newLine().newLine()
               .resetFontSize()
               .normal( "Headline - nutrition is hard! Understanding precisely what a person should do to "
                        + "get the body they want is so, so, so variable. I've learnt a lot since writing this "
                        + "original introduction and I'm not afraid to say I'm wrong, and in fact where results "
                        + "are concerned, I'm happy to be wrong because that means I'm progressing and learning "
                        + "more about how my body, and our bodies, work. This re-introduction will correct "
                        + "the original and give you another perspective on nutrition, one I think is more "
                        + "accurate." )
               .newLine().newLine()
               .build()
      );
      
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withSize( 18 ).bold( "Targets" )
                  .newLine().newLine()
                  .resetFontSize()
                  .normal( "I start off by stating the difference between losing weight and losing fat - a very good "
                           + "and still correct point. However, why did I exclude building muscle? Well, I wasn't "
                           + "interested. That's a mistake... I'm still not interested in building muscle, as a "
                           + "primary objective (yet) but it is a very important piece of the puzzle. I understand "
                           + "building muscle and losing fat to be changes that are the inverse of each other. "
                           + "Losing fat depends on body type, genetics and probably the most important from my "
                           + "experience, hormones! And yes, building muscle is the same. For losing fat, "
                           + "if you have a body type that holds on to fat, you'll find it difficult; if your "
                           + "genetic make-up likes fat, it will be hard to break that. Same principles apply for "
                           + "building muscle. Unfortunately, we don't "
                           + "have much control over those. What do we have control over? Well, hormones, so to "
                           + "say. Do you know what controls our hormones?... our body!... well, yes... but the foods "
                           + "we eat! This is why eating a cookie will have a significantly different effect "
                           + "on your body than eating an avocado." )
                  .newLine().newLine()
                  .build()
      );
      
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withSize( 18 ).bold( "Calories" )
                  .newLine().newLine()
                  .resetFontSize()
                  .normal( "Oh my gosh, what a topic... Calories in Vs carlories out (CICO)... I just have to reduce calories "
                           + "to lose fat... Sorry, no... no, no, no, no, no. Fat storage and burning is entirely controlled "
                           + "by insulin. Fantastic quote from Jason Fung.. 'I can make you fat!'... yes that's right, "
                           + "he can make you fat. You can eat whatever you like! He can make you fat... give you shots "
                           + "of insulin and you will store fat. I'll provide some more research on this in the research "
                           + "area (if it isn't much now, it will be soon!). The take home message - CICO - nope sorry." )
                  .newLine().newLine()
                  .normal( "So where does this CICO come from you ask? Well, calories do play a part. The general concept "
                           + "of consuming more energy than you use is true, the problem is you can't determine what amount of "
                           + "energy you will use... no, not even the calculators. Muscle plays a part here - if you "
                           + "have more muscle, you will burn more calories (nice cycle eh? eat more, build more, can eat more... "
                           + "can build more?? Not quite, but it's a fun idea. Equally, if you exercise more, you'll burn more; "
                           + "if you exercise fasted, you're likely to burn more. If you eat less frequently, you will burn more "
                           + "fat. Here, I'm referring to intermittent fasting - 16:8, Warrior, OMAD - these are for me, with "
                           + "my current understanding, the gold standard for reducing body fat and interestingly building "
                           + "muscle, however I'm only out to prove the former personally at the moment." )
                  .newLine().newLine()
                  .build()
      );
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withSize( 18 ).bold( "So what do I do with my macros?" )
                  .newLine().newLine()
                  .resetFontSize()
                  .normal( "This is really difficult... basically, these calculations I refer to I no longer rate at all. "
                           + "They are a good starting point for finding what fuels your body, but you must figure it out "
                           + "yourself. If anyone disagrees, that's great! Hopefully you have evidence that I'm wrong "
                           + "which means you are on the right track! I'm pleased for you. My evidence suggests it's "
                           + "rubbish. What's my evidence? Well, I dropped weight, oh yes... in fact, I got to what I thought "
                           + "my ideal weight was/should be! but at a cost... energy, well-being, mental clarity, and sadly muscle. " )
                  .newLine().newLine()
                  .bold( "Offense alert!" )
                  .newLine()
                  .normal( "CICO is dangerous! I honestly believe it is dangerous. Why? because you will affect your "
                           + "metabolism and this is not good. In terms of progress, if your metabolism is at 2000kcal let's "
                           + "say, and you start eating 1500kcal, over time your body will adapt. Now your body is happy with "
                           + "1500kcal - you will not be! - so your body is saying to maintain weight, I just need 1500kcal. "
                           + "Rats! Now we need to cut further... Now even if you can keep this going, you will reach a point "
                           + "which I've read is 900-1200kcal where you cannot get adequate nutrition to fuel your body. This "
                           + "is when your body starts to shutdown cutting off vital functions, and one simple result I have "
                           + "personally experienced is body temperature dropping, but there are some very bad things "
                           + "that can happen, so for me, yes dangerous." )
                  .newLine().newLine()
                  .build()
      );
      
      textWrapper.getChildren().add( 
               new TextFlowBuilder()
                  .withSize( 18 ).bold( "Guide me oh master!" )
                  .newLine().newLine()
                  .resetFontSize()
                  .normal( "Damn, yes, what's my advice?... Honestly, sorry x 1000! Ketogenic Diet. That's my recommendation. "
                           + "I love it! Why? I could talk all day about it but google it you'll find why I love it - "
                           + "The feeling of euphoria, being on top of the world all the time, boundless energy, mental "
                           + "clarity like you wouldn't believe, hunger disappears, I'm no longer addicted to eating, to "
                           + "name a few. I'm not trying to convert you, I'm just answering..." )
                  .newLine().newLine()
                  .bold( "Seriously..." )
                  .newLine().newLine()
                  .normal( "Ok, so what I would recommend is eating whole, real foods. Meats and vegetables - I personally "
                           + "like dairy, a lot don't - and stay away from refined carbohydrates such as sugar, wheat, bread, etc. "
                           + "Sounds horrible doesn't it, yeah... wait until you kick it, you'll feel better than you ever "
                           + "have, and when I say kick it, yes it is absolutely an addiction. Finally, please, please, please "
                           + "don't avoid fat - fats from meats, butter, coconut oil, etc - they are perfect for your body! "
                           + "We have evolved based on these foods, not sugar... And if you want some convincing, do some "
                           + "research on medical treatments - keto has been treating people's medical conditions since "
                           + "the 1940's and there are exciting developments in understanding cancer as a metabolic disease (restrict "
                           + "glucose and sugar to help treat it) and dementia potientially being 'type 3 diabetes' because "
                           + "of it's links with insulin - potential treatments using diet!!! What I'm eluding to here is "
                           + "eat healthy foods that your body "
                           + "is built to consume, get rid of the fake addictive rubbish the food industry has hooked us "
                           + "on to fill it's own back pockets (yes, there's some serious hate in there - do your research and "
                           + "you'll agree - people die because of being given the wrong message, they torture themselves "
                           + "with illness and unnecessary medications because people are too concerned with filling "
                           + "their back pockets than looking after their fellow man and giving them real healthy advice) " )
                  .newLine().newLine()
                  .bold( "Disclaimer as before, do your own research, don't trust me, I'm not a doctor (although if you meet "
                           + "a doctor that says keto is bad and fats make you fat, run a mile!). The research section will "
                           + "hopefully provide some more information that's useful, but I'd suggest grabbing some thoughts "
                           + "and ideas I present and googling them, make your own decisions and learn how to control your "
                           + "own body. I know you will all still be focussed on losing fat or gaining muscle but the important "
                           + "part is being healthy... let's say it again... being healthy! and trust me, when you actually "
                           + "are healthy, the pressure you put yourself under to look better, actually goes away when you "
                           + "realise.... hmmm I actually feel awesome eating real food!" )
                  .newLine().newLine()
                  .build()
      );
   }//End Method
   
}//End Class
