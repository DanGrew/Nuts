package uk.dangrew.nuts.persistence.fooditems;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javafx.util.Pair;
import uk.dangrew.jupa.file.protocol.JsonPersistingProtocol;
import uk.dangrew.jupa.json.marshall.ModelMarshaller;
import uk.dangrew.nuts.persistence.dayplan.DayPlanPersistence;
import uk.dangrew.nuts.persistence.goal.calorie.CalorieGoalPersistence;
import uk.dangrew.nuts.persistence.goal.proportion.ProportionGoalPersistence;
import uk.dangrew.nuts.persistence.labels.LabelPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.progress.ProgressSeriesPersistence;
import uk.dangrew.nuts.persistence.settings.SettingsPersistence;
import uk.dangrew.nuts.persistence.stock.StockPersistence;
import uk.dangrew.nuts.persistence.template.TemplatePersistence;
import uk.dangrew.nuts.persistence.weighins.WeightRecordingPersistence;
import uk.dangrew.nuts.store.Database;

public class DatabaseIo {

   private final Database database;
   private final Map< ModelMarshaller, Pair< Runnable, FileIoProtocol > > marshallers;
   
   public DatabaseIo( Database database ) {
      this.database = database;
      this.marshallers = new LinkedHashMap<>();
   }//End Method

   public DatabaseIo withMarshaller( ModelMarshaller marshaller ) {
      return withMarshaller( marshaller, null, FileIoProtocol.ReadWrite );
   }//End Method
   
   public DatabaseIo withMarshaller( ModelMarshaller marshaller, FileIoProtocol protocol ) {
      return withMarshaller( marshaller, null, protocol );
   }//End Method

   public DatabaseIo withMarshaller( ModelMarshaller marshaller, Runnable postProcessor ) {
      return withMarshaller( marshaller, postProcessor, FileIoProtocol.ReadWrite );
   }//End Method
   
   public DatabaseIo withMarshaller( ModelMarshaller marshaller, Runnable postProcessor, FileIoProtocol protocol ) {
      if ( protocol == null ) {
         protocol = FileIoProtocol.ReadWrite;
      }
      this.marshallers.put( marshaller, new Pair<>( postProcessor, protocol ) );
      return this;
   }//End Method
   
   public DatabaseIo marshallerFor( 
            ConceptPersistence persistence, 
            JsonPersistingProtocol protocol,
            Runnable postProcessor, 
            FileIoProtocol fileProtocol
   ) {
      return withMarshaller( 
               new ModelMarshaller( 
                  persistence.structure(), 
                  persistence.readHandles(), 
                  persistence.writeHandles(), 
                  protocol
               ),
               postProcessor,
               fileProtocol
      );
   }//End Method
   
   public DatabaseIo withMarshallerFor( 
            ConceptPersistence persistence, 
            JsonPersistingProtocol protocol,
            FileIoProtocol fileProtocol
   ) {
      return withMarshaller( 
               new ModelMarshaller( 
                  persistence.structure(), 
                  persistence.readHandles(), 
                  persistence.writeHandles(), 
                  protocol
               ),
               null,
               fileProtocol
      );
   }//End Method
   
   public DatabaseIo withMarshallerFor( 
            ConceptPersistence persistence, 
            JsonPersistingProtocol protocol,
            Runnable postProcessor 
   ) {
      return withMarshaller( 
               new ModelMarshaller( 
                  persistence.structure(), 
                  persistence.readHandles(), 
                  persistence.writeHandles(), 
                  protocol
               ),
               postProcessor,
               null
      );
   }//End Method
   
   public DatabaseIo withMarshallerFor( 
            ConceptPersistence persistence, 
            JsonPersistingProtocol protocol
   ) {
      return withMarshaller( 
               new ModelMarshaller( 
                  persistence.structure(), 
                  persistence.readHandles(), 
                  persistence.writeHandles(), 
                  protocol
               ),
               null,
               null
      );
   }//End Method
   
   public DatabaseIo withSettings( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new SettingsPersistence( database.settings() ), protocol );
   }//End Method
   
   public DatabaseIo withFoodItems( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new FoodItemPersistence( database.foodItems() ), protocol );
   }//End Method
   
   public DatabaseIo withMeals( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new MealPersistence<>( database, database.meals() ), protocol );
   }//End Method
   
   public DatabaseIo withTemplates( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new TemplatePersistence<>( database, database.templates() ), protocol );
   }//End Method
   
   public DatabaseIo withDayPlans( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new DayPlanPersistence( database ), protocol );
   }//End Method
   
   public DatabaseIo withCalorieGoals( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new CalorieGoalPersistence( database.calorieGoals() ), protocol );
   }//End Method
   
   public DatabaseIo withProportionGoals( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new ProportionGoalPersistence( database.proportionGoals() ), protocol );
   }//End Method
   
   public DatabaseIo withStockLists( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new StockPersistence( database ), protocol );
   }//End Method
   
   public DatabaseIo withWeightRecordings( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new WeightRecordingPersistence( database ), protocol );
   }//End Method
   
   public DatabaseIo withProgressSeries( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new ProgressSeriesPersistence( database ), protocol );
   }//End Method
   
   public DatabaseIo withLabels( JsonPersistingProtocol protocol ) {
      return withMarshallerFor( new LabelPersistence( database ), protocol );
   }//End Method
   
   public void write() {
      for ( Entry< ModelMarshaller, Pair< Runnable, FileIoProtocol > > entry : marshallers.entrySet() ) {
         Pair< Runnable, FileIoProtocol > conditions = entry.getValue();
         conditions.getValue().processWrite( entry.getKey() );
      }
   }//End Method

   public void read() {
      for ( Entry< ModelMarshaller, Pair< Runnable, FileIoProtocol > > entry : marshallers.entrySet() ) {
         Pair< Runnable, FileIoProtocol > conditions = entry.getValue();
         conditions.getValue().processRead( entry.getKey() );
         Optional.ofNullable( conditions.getKey() ).ifPresent( Runnable::run );
      }
      database.resolver().resolve();
   }//End Method
   
}//End Class
