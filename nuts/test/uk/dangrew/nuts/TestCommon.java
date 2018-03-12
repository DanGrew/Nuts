package uk.dangrew.nuts;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javafx.beans.property.ObjectProperty;

public class TestCommon {

   public static < TypeT > void shouldProvideProperty( ObjectProperty< TypeT > property, TypeT value ) {
      assertThat( property.get(), is( nullValue() ) );
      property.set( value );
      assertThat( property.get(), is( value ) );
   }//End Method
}//End Class
