package uk.dangrew.nuts.system;

public class InstanceVerifier< TypeT > {

   private final Class< ? extends TypeT > type;
   
   public InstanceVerifier( Class< ? extends TypeT > type ) {
      this.type = type;
   }//End Constructor
   
   public void verify( TypeT t ) {
      if ( !t.getClass().isAssignableFrom( type ) ) {
         throw new IllegalArgumentException( "Wrong type of object provided." );
      }
   }//End Method
   
}//End Class
