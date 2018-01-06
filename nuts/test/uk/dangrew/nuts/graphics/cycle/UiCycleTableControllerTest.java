package uk.dangrew.nuts.graphics.cycle;

public class UiCycleTableControllerTest {

//   private UiCycleTable table;
//   @Mock private UiCycleCreationDialog creationDialog;
//   
//   private CycleStore cycles;
//   
//   private GoalImpl baseGoal;
//   private GoalStore goals;
//   private UiCycleTableController systemUnderTest;
//
//   @Before public void initialiseSystemUnderTest() {
//      TestApplication.startPlatform();
//      MockitoAnnotations.initMocks( this );
//      
//      cycles = new CycleStore();
//      goals = new GoalStore();
//      baseGoal = goals.createConcept( "Goal1" );
//      
//      systemUnderTest = new UiCycleTableController( creationDialog, cycles );
//      PlatformImpl.runAndWait( () -> table = new UiCycleTable( systemUnderTest ) );
//      systemUnderTest.associate( table );
//   }//End Method
//
//   @Test public void shouldUpdateRowsWhenAddedToCycles() {
//      AlternatingCycle cycle = cycles.alternatingCycleStore().createConcept( "Cycle1" );
//      assertThat( table.getRows(), hasSize( 1 ) );
//      assertThat( table.getRows().get( 0 ).concept(), is( cycle ) );
//   }//End Method
//   
//   @Test public void shouldUpdateRowsWhenRemovedFromCycles() {
//      AlternatingCycle cycle1 = cycles.alternatingCycleStore().createConcept( "Cycle1" );
//      AlternatingCycle cycle2 = cycles.alternatingCycleStore().createConcept( "Cycle2" );
//      AlternatingCycle cycle3 = cycles.alternatingCycleStore().createConcept( "Cycle3" );
//      assertThat( table.getRows(), hasSize( 3 ) );
//      
//      cycles.alternatingCycleStore().remove( cycle2.properties().id() );
//      assertThat( table.getRows(), hasSize( 2 ) );
//      assertThat( table.getRows().get( 0 ).concept(), is( cycle1 ) );
//      assertThat( table.getRows().get( 1 ).concept(), is( cycle3 ) );
//   }//End Method
//
//   @Test public void shouldUseDialogToCreateNewCycle() {
//      when( creationDialog.friendly_showAndWait() ).thenReturn( Optional.of( new CycleCreationResult( CycleType.Alternating, baseGoal ) ) );
//      Cycle cycle = systemUnderTest.createConcept();
//      assertThat( cycle, is( instanceOf( AlternatingCycle.class ) ) );
//      assertThat( cycle.baseGoal(), is( baseGoal ) );
//   }//End Method
//   
//   @Test public void shouldNotCreateIfNoDialogResult() {
//      when( creationDialog.friendly_showAndWait() ).thenReturn( Optional.empty() );
//      Cycle cycle = systemUnderTest.createConcept();
//      assertThat( cycle, is( nullValue() ) );
//   }//End Method
//   
//   @Test public void shouldRemoveSelectedCycle() {
//      cycles.alternatingCycleStore().createConcept( "Cycle" );
//      assertThat( table.getRows(), hasSize( 1 ) );
//      table.getSelectionModel().select( 0 );
//      systemUnderTest.removeSelectedConcept();
//      assertThat( table.getRows(), hasSize( 0 ) );
//   }//End Method
//   
//   @Test public void shouldNotRemoveIfNotSelected() {
//      cycles.alternatingCycleStore().createConcept( "Cycle" );
//      assertThat( table.getRows(), hasSize( 1 ) );
//      systemUnderTest.removeSelectedConcept();
//      assertThat( table.getRows(), hasSize( 1 ) );
//   }//End Method
//   
//   @Test public void shouldCopySelectedCycle() {
//      cycles.alternatingCycleStore().createConcept( "Cycle" );
//      assertThat( table.getRows(), hasSize( 1 ) );
//      table.getSelectionModel().select( 0 );
//      systemUnderTest.copySelectedConcept();
//      assertThat( table.getRows(), hasSize( 2 ) );
//      assertThat( table.getRows().get( 1 ).concept().properties().nameProperty().get(), is( "Cycle-copy" ) );
//   }//End Method
//   
//   @Test public void shouldNotCopyWhenNotSelected() {
//      cycles.alternatingCycleStore().createConcept( "Cycle" );
//      assertThat( table.getRows(), hasSize( 1 ) );
//      systemUnderTest.copySelectedConcept();
//      assertThat( table.getRows(), hasSize( 1 ) );
//   }//End Method
}//End Class
