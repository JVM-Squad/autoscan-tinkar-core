package dev.ikm.tinkar.integration.snomed.core;


import com.fasterxml.jackson.databind.node.JsonNodeType;
import dev.ikm.tinkar.common.util.uuid.UuidT5Generator;
import dev.ikm.tinkar.entity.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTinkarStarterData extends AbstractSnomedUtil {

    @BeforeAll
    public void setupSuite() throws IOException {
        EntityService = new EntityService();
        // TODO: populating EntityVertex for spined array from json text
//        LOG.info("Loading Json data");
        loadJsonFile("snomedct-starter-data-9.json");

    }

    @AfterAll
    public void teardownSuite() throws IOException{
        cleanUpSnomedData();
    }

    @Test
    @Order(1)
    public void testDataIsPresent()
    {
        Assertions.assertNotNull(getSnomedData());
        System.out.println(getSnomedData().toPrettyString());
    }

    @Test
    @Order(2)
    public void testDataIsJsonObject()
    {
        Assertions.assertTrue(getSnomedData().getNodeType() == JsonNodeType.OBJECT);
    }

    @Test
    @Order(3)
    public void testForNestedFields()
    {
        Assertions.assertTrue(getSnomedFieldDataAsText("namespace").equals("48b004d4-6457-4648-8d58-e3287126d96b"));
        Assertions.assertTrue(getSnomedFieldDataAsText("STAMP", "status").equals("Active"));
    }

    @Test
    @Order(4)
    public void testForNid() {
        Assertions.assertEquals(1, getNid("STAMP", "status"));
    }



    @Test
    @Order(5)
    public void testForMockEntity()
    {
        Assertions.assertEquals(3, EntityService.get().nidForUuids(UuidT5Generator.get("Deloitte User")));
        Assertions.assertEquals(1, EntityService.get().nidForUuids(UuidT5Generator.get("Active")));
    }

    @Test
    @Order(6)
    public void testForStampVersionRecord()
    {
        StampVersionRecord testRecord = getStampVersionRecord(1678570411808L, StampRecordBuilder.builder().leastSignificantBits(1L).mostSignificantBits(2L).nid(3).versions(RecordListBuilder.make()).build());
        Assertions.assertEquals(1678570411808L, testRecord.time());
        Assertions.assertEquals(generateAndPublishConcept("Active").nid(), testRecord.stateNid());
        Assertions.assertEquals(generateAndPublishConcept("Deloitte User").nid(), testRecord.authorNid());
        Assertions.assertEquals(generateAndPublishConcept("SNOMED CT Starter Data Module").nid(), testRecord.moduleNid());
        Assertions.assertEquals(generateAndPublishConcept("Development Path").nid(), testRecord.pathNid());
    }

    @Test
    @Order(6)
    public void testForGeneratingAndPersistingEntity()
    {
        ConceptRecord userConcept = generateAndPublishConcept("Deloitte User");
        ConceptRecord statusConcept = generateAndPublishConcept("Active");
        ConceptRecord moduleConcept = generateAndPublishConcept("SNOMED CT Starter Data Module");
        ConceptRecord pathConcept = generateAndPublishConcept("Development Path");

        UUID userUuid = UuidT5Generator.get("Deloitte User");
        UUID activeUuid = UuidT5Generator.get("Active");
        UUID moduleUuid = UuidT5Generator.get("SNOMED CT Starter Data Module");
        UUID pathUuid = UuidT5Generator.get("Development Path");

        Assertions.assertEquals(1, statusConcept.nid());
        Assertions.assertEquals(1, EntityService.get().nidForUuids(activeUuid));
        Assertions.assertEquals(3, userConcept.nid());
        Assertions.assertEquals(3, EntityService.get().nidForUuids(userUuid));
        Assertions.assertEquals(4, moduleConcept.nid());
        Assertions.assertEquals(4, EntityService.get().nidForUuids(moduleUuid));
        Assertions.assertEquals(5, pathConcept.nid());
        Assertions.assertEquals(5, EntityService.get().nidForUuids(pathUuid));
    }

    @Test
    @Order(7)
    public void testingForEntityService()
    {
        UUID userUuid = UuidT5Generator.get("Deloitte User");
        UUID statusUuid = UuidT5Generator.get("Active");
        UUID moduleUuid = UuidT5Generator.get("SNOMED CT Starter Data Module");
        UUID pathUuid = UuidT5Generator.get("Development Path");
        Assertions.assertEquals(1,
                EntityService.get().nidForUuids(statusUuid));
        Assertions.assertEquals(3,
                EntityService.get().nidForUuids(userUuid));
        Assertions.assertEquals(4,
                EntityService.get().nidForUuids(moduleUuid));
        Assertions.assertEquals(5,
                EntityService.get().nidForUuids(pathUuid));
    }

    @Test
    @Order(8)
    public void testingForStampVersion()
    {
        StampVersionRecord stampRecord = getStampVersionRecord(1234567L, StampRecordBuilder.builder().leastSignificantBits(1L).mostSignificantBits(2L).nid(3).versions(RecordListBuilder.make()).build());

        Assertions.assertEquals(1,
                stampRecord.stateNid());
        Assertions.assertEquals(3,
                stampRecord.authorNid());
        Assertions.assertEquals(4,
                stampRecord.moduleNid());
        Assertions.assertEquals(5,
                stampRecord.pathNid());
        Assertions.assertEquals(1234567L,
                stampRecord.time());
    }

    @Test
    @Order(9)
    public void testingForStampChronology()
            throws IOException
    {
        StampEntity stampRecord = buildStampChronology(getSnomedData());

        Assertions.assertEquals(5,
                stampRecord.pathNid());
    }

}
