package br.com.tommiranda.vm;

public class SegmentToAssemblyVar {

    public static String convertName(String segment) {
        return switch (segment) {
            case "local" -> "@LCL";
            case "argument" -> "@ARG";
            case "this" -> "@THIS";
            case "that" -> "@THAT";
            default -> throw new IllegalStateException("Segment not found");
        };
    }
}
