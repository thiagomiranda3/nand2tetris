package br.com.tommiranda.hack;

public class CInstruction {

    private String dest;
    private String comp;
    private String jump;

    public CInstruction(String instruction) {
        if (instruction.contains("=")) {
            String[] inst = instruction.split("=");

            this.dest = inst[0];
            instruction = inst[1];
        }

        if (instruction.contains(";")) {
            String[] inst = instruction.split(";");

            this.comp = inst[0];
            this.jump = inst[1];
        } else {
            this.comp = instruction;
        }
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getJump() {
        return jump;
    }

    public void setJump(String jump) {
        this.jump = jump;
    }
}
