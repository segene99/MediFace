package ai.allin.facecheck.deepface;

import py4j.GatewayServer;

public class DeepFaceJava {

    public static void main(String[] args) {
        DeepFacePython deepFace = new DeepFacePython();
        GatewayServer server = new GatewayServer(deepFace);
        server.start();
        System.out.println("Java Gateway Server Started");
    }
}

