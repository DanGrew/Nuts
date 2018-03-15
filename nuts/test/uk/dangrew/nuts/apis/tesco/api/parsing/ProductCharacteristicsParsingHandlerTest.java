package uk.dangrew.nuts.apis.tesco.api.parsing;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.model.api.ProductCharacteristics;

public class ProductCharacteristicsParsingHandlerTest {

   private static final Random RANDOMIZER = new Random();
   private ProductCharacteristics characteristicsBeingParsed;
   private ProductCharacteristics characteristicsFromDatabase;
   private ProductCharacteristicsParsingHandler systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      characteristicsBeingParsed = new ProductCharacteristics();
      characteristicsFromDatabase = new ProductCharacteristics();
      systemUnderTest = new ProductCharacteristicsParsingHandler();
      systemUnderTest.resetCharacteristics( characteristicsBeingParsed );
   }//End Method

   @Test public void shouldResetCharacteristics() {
      characteristicsBeingParsed.isFood().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.isDrink().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.healthScore().set( RANDOMIZER.nextDouble() );
      characteristicsBeingParsed.isHazardous().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.storageType().set( UUID.randomUUID().toString() );
      characteristicsBeingParsed.isNonLiquidAnalgesic().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.containsLoperamide().set( RANDOMIZER.nextBoolean() );
      
      systemUnderTest.resetCharacteristics( characteristicsBeingParsed );
      
      assertThat( characteristicsBeingParsed.isFood().get(), is( nullValue() ) );
      assertThat( characteristicsBeingParsed.isDrink().get(), is( nullValue() ) );
      assertThat( characteristicsBeingParsed.healthScore().get(), is( nullValue() ) );
      assertThat( characteristicsBeingParsed.isHazardous().get(), is( nullValue() ) );
      assertThat( characteristicsBeingParsed.storageType().get(), is( nullValue() ) );
      assertThat( characteristicsBeingParsed.isNonLiquidAnalgesic().get(), is( nullValue() ) );
      assertThat( characteristicsBeingParsed.containsLoperamide().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldSetValues() {
      systemUnderTest.setIsFood( true );
      assertThat( characteristicsBeingParsed.isFood().get(), is( true ) );
      
      systemUnderTest.setIsDrink( true );
      assertThat( characteristicsBeingParsed.isDrink().get(), is( true ) );
      
      systemUnderTest.setHealthScore( 10.23 );
      assertThat( characteristicsBeingParsed.healthScore().get(), is( 10.23 ) );
      
      systemUnderTest.setIsHazardous( true );
      assertThat( characteristicsBeingParsed.isHazardous().get(), is( true ) );
      
      systemUnderTest.setStorageType( "anything" );
      assertThat( characteristicsBeingParsed.storageType().get(), is( "anything" ) );
      
      systemUnderTest.setIsNonLiquidAnalgesic( true );
      assertThat( characteristicsBeingParsed.isNonLiquidAnalgesic().get(), is( true ) );

      systemUnderTest.setContainsLoperamide( true );
      assertThat( characteristicsBeingParsed.containsLoperamide().get(), is( true ) );
   }//End Method
   
   @Test public void shouldUpdateDatabaseCharacteristics() {
      characteristicsBeingParsed.isFood().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.isDrink().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.healthScore().set( RANDOMIZER.nextDouble() );
      characteristicsBeingParsed.isHazardous().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.storageType().set( UUID.randomUUID().toString() );
      characteristicsBeingParsed.isNonLiquidAnalgesic().set( RANDOMIZER.nextBoolean() );
      characteristicsBeingParsed.containsLoperamide().set( RANDOMIZER.nextBoolean() );
      
      systemUnderTest.applyCharacteristicsTo( characteristicsFromDatabase );
      
      assertThat( characteristicsBeingParsed.isFood().get(), is( notNullValue() ) );
      assertThat( characteristicsBeingParsed.isDrink().get(), is( notNullValue() ) );
      assertThat( characteristicsBeingParsed.healthScore().get(), is( notNullValue() ) );
      assertThat( characteristicsBeingParsed.isHazardous().get(), is( notNullValue() ) );
      assertThat( characteristicsBeingParsed.storageType().get(), is( notNullValue() ) );
      assertThat( characteristicsBeingParsed.isNonLiquidAnalgesic().get(), is( notNullValue() ) );
      assertThat( characteristicsBeingParsed.containsLoperamide().get(), is( notNullValue() ) );
      
      assertThat( characteristicsBeingParsed.isFood().get(), is( characteristicsFromDatabase.isFood().get() ) );
      assertThat( characteristicsBeingParsed.isDrink().get(), is( characteristicsFromDatabase.isDrink().get() ) );
      assertThat( characteristicsBeingParsed.healthScore().get(), is( characteristicsFromDatabase.healthScore().get() ) );
      assertThat( characteristicsBeingParsed.isHazardous().get(), is( characteristicsFromDatabase.isHazardous().get() ) );
      assertThat( characteristicsBeingParsed.storageType().get(), is( characteristicsFromDatabase.storageType().get() ) );
      assertThat( characteristicsBeingParsed.isNonLiquidAnalgesic().get(), is( characteristicsBeingParsed.isNonLiquidAnalgesic().get() ) );
      assertThat( characteristicsBeingParsed.containsLoperamide().get(), is( characteristicsFromDatabase.containsLoperamide().get() ) );
   }//End Method
}//End Class
