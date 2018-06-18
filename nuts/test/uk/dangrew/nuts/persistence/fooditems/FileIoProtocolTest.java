package uk.dangrew.nuts.persistence.fooditems;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;import com.sun.swing.internal.plaf.metal.resources.metal;

import uk.dangrew.jupa.json.marshall.ModelMarshaller;
import uk.dangrew.kode.launch.TestApplication;

public class FileIoProtocolTest {

   @Mock private ModelMarshaller marshaller;
   
   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
   }//End Method

   @Test public void shouldAllowReadAndWriteCorrectly() {
      FileIoProtocol.ReadOnly.processRead( marshaller );
      verify( marshaller ).read();
      
      FileIoProtocol.ReadWrite.processRead( marshaller );
      verify( marshaller, times( 2 ) ).read();
      
      FileIoProtocol.WriteOnly.processRead( marshaller );
      verify( marshaller, times( 2 ) ).read();
      
      FileIoProtocol.WriteOnly.processWrite( marshaller );
      verify( marshaller ).write();
      
      FileIoProtocol.ReadWrite.processWrite( marshaller );
      verify( marshaller, times( 2 ) ).write();
      
      FileIoProtocol.ReadOnly.processWrite( marshaller );
      verify( marshaller, times( 2 ) ).write();
   }//End Method

}//End Class
