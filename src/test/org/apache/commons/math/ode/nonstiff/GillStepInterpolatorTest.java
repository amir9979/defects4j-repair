/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.math.ode.nonstiff;

import junit.framework.*;
import java.util.Random;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import org.apache.commons.math.ode.ContinuousOutputModel;
import org.apache.commons.math.ode.DerivativeException;
import org.apache.commons.math.ode.IntegratorException;
import org.apache.commons.math.ode.nonstiff.GillIntegrator;

public class GillStepInterpolatorTest
  extends TestCase {

  public GillStepInterpolatorTest(String name) {
    super(name);
  }

  public void testSerialization()
    throws DerivativeException, IntegratorException,
           IOException, ClassNotFoundException {

    TestProblem3 pb = new TestProblem3(0.9);
    double step = (pb.getFinalTime() - pb.getInitialTime()) * 0.0003;
    GillIntegrator integ = new GillIntegrator(step);
    integ.setStepHandler(new ContinuousOutputModel());
    integ.integrate(pb,
                    pb.getInitialTime(), pb.getInitialState(),
                    pb.getFinalTime(), new double[pb.getDimension()]);

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream    oos = new ObjectOutputStream(bos);
    oos.writeObject(integ.getStepHandler());

    assertTrue(bos.size () > 700000);
    assertTrue(bos.size () < 701000);

    ByteArrayInputStream  bis = new ByteArrayInputStream(bos.toByteArray());
    ObjectInputStream     ois = new ObjectInputStream(bis);
    ContinuousOutputModel cm  = (ContinuousOutputModel) ois.readObject();

    Random random = new Random(347588535632l);
    double maxError = 0.0;
    for (int i = 0; i < 1000; ++i) {
      double r = random.nextDouble();
      double time = r * pb.getInitialTime() + (1.0 - r) * pb.getFinalTime();
      cm.setInterpolatedTime(time);
      double[] interpolatedY = cm.getInterpolatedState ();
      double[] theoreticalY  = pb.computeTheoreticalState(time);
      double dx = interpolatedY[0] - theoreticalY[0];
      double dy = interpolatedY[1] - theoreticalY[1];
      double error = dx * dx + dy * dy;
      if (error > maxError) {
        maxError = error;
      }
    }

    assertTrue(maxError < 0.003);

  }

  public static Test suite() {
    return new TestSuite(GillStepInterpolatorTest.class);
  }

}
