from multiprocessing import Process
from kafka import KafkaProducer
import random
import json
from datetime import datetime
import socket
import copy


## PARAMETERS
enableTaskIVIM = True
enableTaskDENM = True
enableTaskUDP = True

KAFKA_SERVER = "127.0.0.1:9092"
UDP_URI = "127.0.0.1"
UDP_PORT = 4400

SAMPLE_DENM = {"messageType":"DENM","priority":0,"certified":False,"header":{"protocolVersion":{"value":1},"messageID":{"value":1},"stationID":{"value":1234567}},"denm":{"management":{"actionID":{"originatingStationID":{"value":20},"sequenceNumber":{"value":30}},"detectionTime":{"value":45000000000},"referenceTime":{"value":1},"termination":None,"eventPosition":{"latitude":{"value":40487111},"longitude":{"value":-79494789},"positionConfidenceEllipse":{"semiMajorConfidence":{"value":500},"semiMinorConfidence":{"value":400},"semiMajorOrientation":{"value":10}},"altitude":{"altitudeValue":{"value":2000},"altitudeConfidence":"alt_000_02"}},"relevanceDistance":None,"relevanceTrafficDirection":None,"validityDuration":{"value":600},"transmissionInterval":{"value":1},"stationType":{"value":0}},"situation":{"informationQuality":{"value":1},"eventType":{"causeCode":{"value":3},"subCauseCode":{"value":0}},"linkedCause":None,"eventHistory":None},"location":{"eventSpeed":{"speedValue":{"value":0},"speedConfidence":{"value":1}},"eventPositionHeading":{"headingValue":{"value":0},"headingConfidence":{"value":10}},"traces":[[{"pathPosition":{"deltaLatitude":{"value":20},"deltaLongitude":{"value":20},"deltaAltitude":{"value":12800}},"pathDeltaTime":{"value":1}},{"pathPosition":{"deltaLatitude":{"value":22},"deltaLongitude":{"value":22},"deltaAltitude":{"value":12800}},"pathDeltaTime":None}]],"roadType":"urban_NoStructuralSeparationToOppositeLanes"},"alacarte":{"lanePosition":None,"impactReduction":{"heightLonCarrLeft":{"value":1},"heightLonCarrRight":{"value":1},"posLonCarrLeft":{"value":1},"posLonCarrRight":{"value":1},"positionOfPillars":[{"value":1}],"posCentMass":{"value":63},"wheelBaseVehicle":{"value":1},"turningRadius":{"value":1},"posFrontAx":{"value":1},"positionOfOccupants":{"row1LeftOccupied":True,"row1RightOccupied":True,"row1MidOccupied":True,"row1NotDetectable":True,"row1NotPresent":True,"row2LeftOccupied":True,"row2RightOccupied":True,"row2MidOccupied":False,"row2NotDetectable":False,"row2NotPresent":False,"row3LeftOccupied":False,"row3RightOccupied":False,"row3MidOccupied":False,"row3NotDetectable":False,"row3NotPresent":True,"row4LeftOccupied":False,"row4RightOccupied":False,"row4MidOccupied":False,"row4NotDetectable":False,"row4NotPresent":True},"vehicleMass":{"value":20},"requestResponseIndication":"response"},"externalTemperature":{"value":1},"roadWorks":{"lightBarSirenInUse":{"lightBarActivated":True,"sirenActivated":True},"closedLanes":{"hardShoulderStatus":"availableForStopping","drivingLaneStatus":[False,True,True]},"restriction":[{"value":0}],"speedLimit":{"value":20},"incidentIndication":{"causeCode":{"value":0},"subCauseCode":{"value":0}},"recommendedPath":[{"latitude":{"value":20},"longitude":{"value":20},"positionConfidenceEllipse":{"semiMajorConfidence":{"value":1},"semiMinorConfidence":{"value":1},"semiMajorOrientation":{"value":0}},"altitude":{"altitudeValue":{"value":200},"altitudeConfidence":"alt_000_02"}}],"startingPointSpeedLimit":None,"trafficFlowRule":None,"referenceDenms":None},"positioningSolution":"noPositioningSolution","stationaryVehicle":{"stationarySince":"equalOrGreater15Minutes","stationaryCause":{"causeCode":{"value":3},"subCauseCode":{"value":0}},"carryingDangerousGoods":None,"numberOfOccupants":{"value":30},"vehicleIdentification":{"vds":{},"wminumber":{}},"energyStorageType":{"hydrogenStorage":False,"electricEnergyStorage":False,"liquidPropaneGas":False,"compressedNaturalGas":False,"diesel":False,"gasoline":True,"ammonia":False}}}}}
SAMPLE_DENM_FOR_UDP = {"messageType":"DENM","priority":0,"certified":False,"header":{"protocolVersion":{"value":1},"messageID":{"value":0},"stationID":{"value":0}},"denm":{"management":{"actionID":{"originatingStationID":{"value":0},"sequenceNumber":{"value":0}},"detectionTime":{"value":0},"referenceTime":{"value":0},"termination":None,"eventPosition":{"latitude":{"value":900000001},"longitude":{"value":1800000001},"positionConfidenceEllipse":{"semiMajorConfidence":{"value":4095},"semiMinorConfidence":{"value":4095},"semiMajorOrientation":{"value":3601}},"altitude":{"altitudeValue":{"value":800001},"altitudeConfidence":"unavailable"}},"relevanceDistance":None,"relevanceTrafficDirection":None,"validityDuration":{"value":600},"transmissionInterval":None,"stationType":{"value":0}},"situation":None,"location":None,"alacarte":None}}
SAMPLE_IVIM = {"messageType":"IVIM","priority":0,"certified":False,"header":{"protocolVersion":{"value":1},"messageID":{"value":0},"stationID":{"value":0}},"ivi":{"mandatory":{"serviceProviderId":{"countryCode":[],"providerIdentifier":{"value":0}},"iviIdentificationNumber":{"value":1},"timeStamp":None,"validFrom":None,"validTo":None,"connectedIviStructures":None,"iviStatus":{"value":0}},"optional":None}}
##


def createIvimFromSample():
    ivim = copy.deepcopy(SAMPLE_IVIM)
    ivim['priority'] = random.randint(0, 10)
    ivim['certified'] = random.choice((True, False))
    ivim['header']['protocolVersion']['value'] = random.randint(0, 255)
    ivim['header']['messageID']['value'] = random.randint(0, 255)
    ivim['header']['stationID']['value'] = random.randint(0, 4294967295)
    ivim['ivi']['mandatory']['validFrom'] = random.randint(0,4398046511103)
    ivim['ivi']['mandatory']['validTo'] = random.randint(0,4398046511103)

    return ivim


def createDenmFromSample(udp=False):
    denm = copy.deepcopy(SAMPLE_DENM if not udp else SAMPLE_DENM_FOR_UDP)
    denm['priority'] = random.randint(0,10)
    denm['certified'] = random.choice((True, False))
    denm['header']['protocolVersion']['value'] = random.randint(0,255)
    denm['header']['messageID']['value'] = random.randint(0,255)
    denm['header']['stationID']['value'] = random.randint(0,4294967295)
    denm['denm']['management']['actionID']['originatingStationID']['value'] = random.randint(0,4294967295)
    denm['denm']['management']['actionID']['sequenceNumber']['value'] = random.randint(0,65535)
    denm['denm']['management']['detectionTime']['value'] = random.randint(0,4398046511103)
    denm['denm']['management']['referenceTime']['value'] = random.randint(0,4398046511103)
    denm['denm']['management']['validityDuration']['value'] = random.randint(0,86400)

    return denm


def taskIVIM(limit=-1):
    print("*** Executing Task-IVIM ***")

    count = 0
    while True:
        ivim = createIvimFromSample()

        # kafka send
        producer = KafkaProducer(bootstrap_servers=KAFKA_SERVER,
                                 value_serializer=lambda v: json.dumps(v).encode('utf-8'))
        future = producer.send('in-ivim', ivim)
        result = future.get(timeout=60)

        count += 1
        #print("[Task-IVIM] - messages sent: {}".format(count)) if (count % 100 == 0) else None

        if count == limit:
            print("*** Ending Task-IVIM ***")
            producer.close()
            break


def taskDENM(limit=-1):
    print("*** Executing Task-DENM ***")

    count = 0
    while True:
        denm = createDenmFromSample()

        # kafka send
        producer = KafkaProducer(bootstrap_servers=KAFKA_SERVER,
                                 value_serializer=lambda v: json.dumps(v).encode('utf-8'))
        future = producer.send('in-denm', denm)
        result = future.get(timeout=60)

        count += 1
        #print("[Task-DENM] - messages sent: {}".format(count)) if (count % 100 == 0) else None

        if count == limit:
            print("*** Ending Task-DENM ***")
            producer.close()
            break


def taskUDP(limit=-1):
    print("*** Executing Task-UDP ***")

    count = 0
    while True:
        denm = createDenmFromSample(udp=True)

        # udp send
        sock = socket.socket(socket.AF_INET,  # Internet
                             socket.SOCK_DGRAM)  # UDP
        sock.sendto(json.dumps(denm).encode('utf-8'), (UDP_URI, UDP_PORT))

        #print("[Task-UDP] - messages sent: {}".format(count)) if (count % 100 == 0) else None

        count += 1
        if count == limit:
            print("*** Ending Task-UDP ***")
            sock.close()
            break


##### EXECUTE #####
if __name__ == '__main__':
    send_limit = -1
    while True:
        try:
            send_limit = int(input("Send Limit (-1: no limits) \n"))
            break
        except ValueError as e:
            continue

    print("----- START TEST -----")
    print("----- Number of executions: {} -----".format(send_limit))
    try:
        processIvim = Process(target=taskIVIM, args=(send_limit,)) if enableTaskIVIM else None
        processDenm = Process(target=taskDENM, args=(send_limit,)) if enableTaskDENM else None
        processUdp = Process(target=taskUDP, args=(send_limit,)) if enableTaskUDP else None

        processIvim.start() if enableTaskIVIM else None
        processDenm.start() if enableTaskDENM else None
        processUdp.start() if enableTaskUDP else None

        processIvim.join() if enableTaskIVIM else None
        processDenm.join() if enableTaskDENM else None
        processUdp.join() if enableTaskUDP else None

        print("----- END TEST -----")
    except KeyboardInterrupt as k:
        print("----- END TEST -----")
    except Exception as e:
        print(e)