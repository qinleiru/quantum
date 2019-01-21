package com.quantum.state;

import com.quantum.tools.Tools;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;

public interface QuantumState {

    double[] getState();
    void setState(double[] state);
    int getParticles();
    void setParticles(int particles);
    ArrayList<String> getParticlesName();
    void setParticlesName(int pos,String name);

}
