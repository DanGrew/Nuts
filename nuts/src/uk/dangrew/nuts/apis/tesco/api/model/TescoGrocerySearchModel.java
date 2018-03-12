package uk.dangrew.nuts.apis.tesco.api.model;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoGrocerySearchModel {
   
   private final TescoFoodDescriptionStore store;
   
   private String image;
   private String superDepartment;
   private String tpnb;
   private Double unitOfSale;
   private final List< String > description;
   private String unitQuantity;
   private String promotionDescription;
   private String contentsMeasureType;
   private String name;
   private Double averageSellingUnitWeight;
   private String id;
   private Double contentsQuantity;
   private String department;
   private Double price;
   private Double unitPrice;
   private Boolean isSpecialOffer;
   
   public TescoGrocerySearchModel( TescoFoodDescriptionStore store ) {
      this.store = store;
      this.description = new ArrayList<>();
   }//End Constructor

   public void startResult( String key ) {
      image = null;
      superDepartment = null;
      tpnb = null;
      unitOfSale = null;
      description.clear();
      unitQuantity = null;
      promotionDescription = null;
      contentsMeasureType = null;
      name = null;
      averageSellingUnitWeight = null;
      id = null;
      contentsQuantity = null;
      department = null;
      price = null;
      unitPrice = null;
      isSpecialOffer = null;
   }//End Method
   
   public void finishResult( String key ) {
      if ( tpnb == null ) {
         System.out.println( "No tpnb provided!" );
         return;
      }
      TescoFoodDescription item = store.get( tpnb );
      if ( item == null ) {
         item = new TescoFoodDescription( tpnb, name );
         store.store( item );
      }
      
      item.groceryProperties().image().set( image );
      item.groceryProperties().superDepartment().set( superDepartment );
      item.groceryProperties().tpnb().set( tpnb );
      item.groceryProperties().unitOfSale().set( unitOfSale );
      item.groceryProperties().description().clear();
      item.groceryProperties().description().addAll( description );
      item.groceryProperties().unitQuantity().set( unitQuantity );
      item.groceryProperties().promotionDescription().set( promotionDescription );
      item.groceryProperties().contentsMeasureType().set( contentsMeasureType );
      item.groceryProperties().name().set( name );
      item.groceryProperties().averageSellingUnitWeight().set( averageSellingUnitWeight );
      item.groceryProperties().id().set( id );
      item.groceryProperties().contentsQuantity().set( contentsQuantity );
      item.groceryProperties().department().set( department );
      item.groceryProperties().price().set( price );
      item.groceryProperties().unitPrice().set( unitPrice );
      item.groceryProperties().isSpecialOffer().set( isSpecialOffer );
   }//End Method
   
   public void setImageLink( String key, String value ) {
      this.image = value.replaceFirst( "http:", "https:" );
   }//End Method
   
   public void setSuperDepartment( String key, String value ) {
      this.superDepartment = value;
   }//End Method

   public void setTpnb( String key, String value ) {
      this.tpnb = value;
   }//End Method
   
   public void setUnitOfSale( String key, Double value ) {
      this.unitOfSale = value;
   }//End Method
   
   public void addDescription( String key, String value ) {
      this.description.add( value );
   }//End Method
   
   public void setUnitQuantity( String key, String value ) {
      this.unitQuantity = value;
   }//End Method
   
   public void setPromotionDescription( String key, String value ) {
      this.promotionDescription = value;
   }//End Method
   
   public void setContentsMeasureType( String key, String value ) {
      this.contentsMeasureType = value;
   }//End Method
   
   public void setName( String key, String value ) {
      this.name = value;
   }//End Method
   
   public void setAverageSellingUnitWeight( String key, Double value ) {
      this.averageSellingUnitWeight = value;
   }//End Method
   
   public void setId( String key, String value ) {
      this.id = value;
   }//End Method
   
   public void setContentsQuantity( String key, Double value ) {
      this.contentsQuantity = value;
   }//End Method
   
   public void setDepartment( String key, String value ) {
      this.department = value;
   }//End Method
   
   public void setPrice( String key, Double value ) {
      this.price = value;
   }//End Method
   
   public void setUnitPrice( String key, Double value ) {
      this.unitPrice = value;
   }//End Method
   
   public void setIsSpecialOffer( String key, Boolean value ) {
      this.isSpecialOffer = value;
   }//End Method
   
}//End Class
