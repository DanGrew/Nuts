/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

/**
 * {@link StorageType} provides the type of storage method for the {@link FoodItem}. Useful when
 * identifying when to restock.
 */
public enum StorageType {
   
   Fresh,
   Cupboard,
   Tinned,
   Frozen,
   Unknown

}//End Enum
