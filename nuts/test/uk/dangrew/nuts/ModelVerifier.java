package uk.dangrew.nuts;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;

public class ModelVerifier< SutT > {

   private static final Random random = new Random();
   private final SutT sut;
   
   public ModelVerifier( SutT sut ) {
      this.sut = sut;
   }//End Constructor
   
   public < TypeT > ModelVerifier< SutT > shouldProvideProperty( Function< SutT, ObjectProperty< TypeT > > supplier, TypeT value ) {
      return shouldProvideProperty( supplier, null );
   }//End Method
   
   public < TypeT > ModelVerifier< SutT > shouldProvideProperty( Function< SutT, ObjectProperty< TypeT > > supplier, TypeT initialValue, TypeT value ) {
      shouldProvideObject( supplier );
      assertThat( supplier.apply( sut ).get(), is( initialValue ) );
      supplier.apply( sut ).set( value );
      assertThat( supplier.apply( sut ).get(), is( value ) );
      return this;
   }//End Method
   
   public ModelVerifier< SutT > shouldProvideObject( Function< SutT, ? > supplier ) {
      assertThat( supplier.apply( sut ), is( notNullValue() ) );
      assertThat( supplier.apply( sut ), is( supplier.apply( sut ) ) );
      return this;
   }//End Method
   
   public ModelVerifier< SutT > shouldProvideStringProperty( Function< SutT, ObjectProperty< String > > supplier ) {
      String value = UUID.randomUUID().toString();
      shouldProvideProperty( supplier, value );
      return this;
   }//End Method
   
   public ModelVerifier< SutT > shouldProvideDoubleProperty( Function< SutT, ObjectProperty< Double > > supplier ) {
      return shouldProvideDoubleProperty( supplier, null );
   }//End Method
   
   public ModelVerifier< SutT > shouldProvideDoubleProperty( Function< SutT, ObjectProperty< Double > > supplier, Double initialValue ) {
      double value = random.nextDouble();
      return shouldProvideProperty( supplier, initialValue, value );
   }//End Method
   
   public ModelVerifier< SutT > shouldProvideBooleanProperty( Function< SutT, ObjectProperty< Boolean > > supplier ) {
      boolean value = random.nextBoolean();
      shouldProvideProperty( supplier, value );
      return this;
   }//End Method
   
   public < TypeT > ModelVerifier< SutT > shouldProvideCollection( Function< SutT, Collection< TypeT > > supplier, TypeT value ) {
      shouldProvideObject( supplier );
      assertThat( supplier.apply( sut ), is( notNullValue() ) );
      supplier.apply( sut ).add( value );
      assertThat( supplier.apply( sut ), contains( value ) );
      return this;
   }//End Method
   
   public ModelVerifier< SutT > shouldProvideCollection( Function< SutT, Collection< String > > supplier ) {
      shouldProvideCollection( supplier, UUID.randomUUID().toString() );
      return this;
   }//End Method
   
   public < KeyT, ValueT > ModelVerifier< SutT > shouldProvideObservableMap( Function< SutT, ObservableMap< KeyT, ValueT > > supplier, KeyT key, ValueT value ) {
      shouldProvideObject( supplier );
      assertThat( supplier.apply( sut ), is( notNullValue() ) );
      supplier.apply( sut ).put( key, value );
      assertThat( supplier.apply( sut ), hasEntry( key, value ) );
      return this;
   }//End Method
}//End Class
