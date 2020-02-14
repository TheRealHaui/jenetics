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
package io.jenetics.example.image;

import java.awt.*;

import javax.swing.*;

/**
 * Panel for showing/changing the engine parameters.
 */
public class EngineParamPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates new form EngineParamPanel
	 */
	public EngineParamPanel() {
		initComponents();

		_populationSizeSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getPopulationSize(),
			EngineParam.MIN_POPULATION_SIZE,
			EngineParam.MAX_POPULATION_SIZE,
			1
		));

		_tournamentSizeSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getTournamentSize(),
			EngineParam.MIN_TOURNAMENT_SIZE,
			EngineParam.MAX_TOURNAMENT_SIZE,
			1
		));

		_mutationRateSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getMutationRate(),
			EngineParam.MIN_MUTATION_RATE,
			EngineParam.MAX_MUTATION_RATE,
			0.001
		));

		_mutationChangeSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getMutationMultitude(),
			EngineParam.MIN_MUTATION_CHANGE,
			EngineParam.MAX_MUTATION_CHANGE,
			0.001
		));

		_polygonLengthSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getPolygonLength(),
			EngineParam.MIN_POLYGON_LENGTH,
			EngineParam.MAX_POLYGON_LENGTH,
			1
		));

		_polygonCountSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getPolygonCount(),
			EngineParam.MIN_POLYGON_COUNT,
			EngineParam.MAX_POLYGON_COUNT,
			1
		));

		_referenceImageWidthSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getReferenceImageSize().width,
			EngineParam.MIN_REF_IMAGE_SIZE.width,
			EngineParam.MAX_REF_IMAGE_SIZE.width,
			1
		));

		_referenceImageHeightSpinner.setModel(new SpinnerNumberModel(
			EngineParam.DEFAULT.getReferenceImageSize().height,
			EngineParam.MIN_REF_IMAGE_SIZE.height,
			EngineParam.MAX_REF_IMAGE_SIZE.height,
			1
		));
	}

	void setEngineParam(final EngineParam param) {
		_populationSizeSpinner.setValue(param.getPopulationSize());
		_tournamentSizeSpinner.setValue(param.getTournamentSize());
		_mutationRateSpinner.setValue(param.getMutationRate());
		_mutationChangeSpinner.setValue(param.getMutationMultitude());
		_polygonLengthSpinner.setValue(param.getPolygonLength());
		_polygonCountSpinner.setValue(param.getPolygonCount());
		_referenceImageWidthSpinner.setValue(param.getReferenceImageSize().width);
		_referenceImageHeightSpinner.setValue(param.getReferenceImageSize().height);
	}

	EngineParam getEngineParam() {
		return EngineParam.of(
			(Integer)_populationSizeSpinner.getValue(),
			(Integer)_tournamentSizeSpinner.getValue(),
			((Number)_mutationRateSpinner.getValue()).floatValue(),
			((Number)_mutationChangeSpinner.getValue()).floatValue(),
			(Integer)_polygonLengthSpinner.getValue(),
			(Integer)_polygonCountSpinner.getValue(),
			new Dimension(
				(Integer)_referenceImageWidthSpinner.getValue(),
				(Integer)_referenceImageHeightSpinner.getValue()
			)
		);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		_populationSizeSpinner.setEnabled(enabled);
		_tournamentSizeSpinner.setEnabled(enabled);
		_mutationRateSpinner.setEnabled(enabled);
		_mutationChangeSpinner.setEnabled(enabled);
		_polygonLengthSpinner.setEnabled(enabled);
		_polygonCountSpinner.setEnabled(enabled);
		_referenceImageWidthSpinner.setEnabled(enabled);
		_referenceImageHeightSpinner.setEnabled(enabled);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        _populationSizeLabel = new javax.swing.JLabel();
        _populationSizeSpinner = new javax.swing.JSpinner();
        _tournamentSizeLabel = new javax.swing.JLabel();
        _tournamentSizeSpinner = new javax.swing.JSpinner();
        _mutationRateLabel = new javax.swing.JLabel();
        _mutationChangeLabel = new javax.swing.JLabel();
        _polygonLengthLabel = new javax.swing.JLabel();
        _polygonLengthSpinner = new javax.swing.JSpinner();
        _polygonCountLabel = new javax.swing.JLabel();
        _polygonCountSpinner = new javax.swing.JSpinner();
        _referenceImageSizeLabel = new javax.swing.JLabel();
        _mutationRateSpinner = new javax.swing.JSpinner();
        _mutationChangeSpinner = new javax.swing.JSpinner();
        _referenceImageWidthSpinner = new javax.swing.JSpinner();
        _referenceImageHeightSpinner = new javax.swing.JSpinner();

        setLayout(new java.awt.GridBagLayout());

        _populationSizeLabel.setText("Population size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_populationSizeLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_populationSizeSpinner, gridBagConstraints);

        _tournamentSizeLabel.setText("Tournament size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_tournamentSizeLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_tournamentSizeSpinner, gridBagConstraints);

        _mutationRateLabel.setText("Mutation rate:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_mutationRateLabel, gridBagConstraints);

        _mutationChangeLabel.setText("Mutation change:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_mutationChangeLabel, gridBagConstraints);

        _polygonLengthLabel.setText("Polygon length:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_polygonLengthLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_polygonLengthSpinner, gridBagConstraints);

        _polygonCountLabel.setText("Polygon count:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_polygonCountLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_polygonCountSpinner, gridBagConstraints);

        _referenceImageSizeLabel.setText("Reference image size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_referenceImageSizeLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_mutationRateSpinner, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_mutationChangeSpinner, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_referenceImageWidthSpinner, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(_referenceImageHeightSpinner, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel _mutationChangeLabel;
    private javax.swing.JSpinner _mutationChangeSpinner;
    private javax.swing.JLabel _mutationRateLabel;
    private javax.swing.JSpinner _mutationRateSpinner;
    private javax.swing.JLabel _polygonCountLabel;
    private javax.swing.JSpinner _polygonCountSpinner;
    private javax.swing.JLabel _polygonLengthLabel;
    private javax.swing.JSpinner _polygonLengthSpinner;
    private javax.swing.JLabel _populationSizeLabel;
    private javax.swing.JSpinner _populationSizeSpinner;
    private javax.swing.JSpinner _referenceImageHeightSpinner;
    private javax.swing.JLabel _referenceImageSizeLabel;
    private javax.swing.JSpinner _referenceImageWidthSpinner;
    private javax.swing.JLabel _tournamentSizeLabel;
    private javax.swing.JSpinner _tournamentSizeSpinner;
    // End of variables declaration//GEN-END:variables
}
