package uk.dangrew.nuts.persistence.fooditems;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javafx.util.Pair;
import uk.dangrew.jupa.json.marshall.ModelMarshaller;
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
      this.marshallers.put( marshaller, new Pair<>( postProcessor, protocol ) );
      return this;
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
