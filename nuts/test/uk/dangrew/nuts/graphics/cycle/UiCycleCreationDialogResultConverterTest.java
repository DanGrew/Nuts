package uk.dangrew.nuts.graphics.cycle;

public class UiCycleCreationDialogResultConverterTest {

//   private ButtonType acceptButtonType;
//   private ButtonType cancelButtonType;
//   private ComboBox< CycleType > types;
//   private ObservableList< GoalImpl > goalOptions;
//   private ComboBox< GoalImpl > goalImpls;
//   private UiCycleCreationDialogResultConverter systemUnderTest;
//
//   @Before public void initialiseSystemUnderTest() {
//      TestApplication.startPlatform();
//      
//      acceptButtonType = ButtonType.OK;
//      cancelButtonType = ButtonType.CANCEL;
//      
//      types = new ComboBox<>( FXCollections.observableArrayList( Arrays.asList( CycleType.values() ) ) );
//      
//      goalOptions = FXCollections.observableArrayList();
//      goalOptions.add( new GoalImpl( "GoalA" ) );
//      goalOptions.add( new GoalImpl( "GoalB" ) );
//      goalOptions.add( new GoalImpl( "GoalC" ) );
//      
//      goalImpls = new ComboBox<>( goalOptions );
//      systemUnderTest = new UiCycleCreationDialogResultConverter( acceptButtonType, types, goalImpls );
//   }//End Method
//
//   @Test public void shouldConstructResultFromChosenOptions() {
//      types.getSelectionModel().select( 0 );
//      goalImpls.getSelectionModel().select( 1 );
//      
//      CycleCreationResult result = systemUnderTest.call( acceptButtonType );
//      assertThat( result.type(), is( CycleType.Alternating ) );
//      assertThat( result.baseGoal(), is( goalOptions.get( 1 ) ) );
//   }//End Method
//   
//   @Test public void shouldProvideNoResultWhenOptionMissing() {
//      CycleCreationResult result = systemUnderTest.call( acceptButtonType );
//      assertThat( result, is( nullValue() ) );
//      
//      types.getSelectionModel().select( 0 );
//      result = systemUnderTest.call( acceptButtonType );
//      assertThat( result, is( nullValue() ) );
//      
//      types.getSelectionModel().clearSelection();
//      goalImpls.getSelectionModel().select( 1 );
//      result = systemUnderTest.call( acceptButtonType );
//      assertThat( result, is( nullValue() ) );
//   }//End Method
//   
//   @Test public void shouldProvideNoResultWhenCancelled() {
//      types.getSelectionModel().select( 0 );
//      goalImpls.getSelectionModel().select( 1 );
//      
//      CycleCreationResult result = systemUnderTest.call( cancelButtonType );
//      assertThat( result, is( nullValue() ) );
//   }//End Method

}//End Class
