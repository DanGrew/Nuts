package uk.dangrew.nuts.persistence.fooditems;

import uk.dangrew.jupa.json.marshall.ModelMarshaller;

public enum FileIoProtocol {

   ReadWrite( true, true ),
   ReadOnly( true, false ),
   WriteOnly( false, true );
   
   private final boolean readAllowed;
   private final boolean writeAllowed;
   
   private FileIoProtocol( boolean readAllowed, boolean writeAllowed ) {
      this.readAllowed = readAllowed;
      this.writeAllowed = writeAllowed;
   }//End Constructor
   
   public void processRead( ModelMarshaller marshaller ){
      if ( readAllowed ) marshaller.read();
   }//End Method
   
   public void processWrite( ModelMarshaller marshaller ){
      if ( writeAllowed ) marshaller.write();
   }//End Method
}//End Enum
