package org.grouplens.lenskit.eval.predict;

import static org.junit.Assert.*;
import org.grouplens.lenskit.data.vector.MutableSparseVector;
import org.junit.Test;


public class TestNDCG {

	@Test
	public void testComputeNDCG() {

		long[] items = {1, 2, 3, 4, 5, 6, 7 ,8, 9, 10};
		double[] ratings1 = {5, 4, 4, 3, 5, 3, 4, 3, 2, 5};
		double[] ratings2 = {5, 5, 4, 4, 4, 3, 2, 2, 3, 4};
		double[] ratings3 = {4, 5, 4, 2, 3, 1, 3, 4, 5, 2};
		MutableSparseVector v1 = MutableSparseVector.wrap(items,ratings1);
		MutableSparseVector v2 = MutableSparseVector.wrap(items, ratings2);
		MutableSparseVector v3 = MutableSparseVector.wrap(items, ratings3);
		assertEquals(NDCGEvaluator.computeDCG(RankEvaluationUtils.sortKeys(v1), v1), 22.0418, 0.0001);
		assertEquals(NDCGEvaluator.computeDCG(RankEvaluationUtils.sortKeys(v2), v2), 21.0954, 0.0001);
		assertEquals(NDCGEvaluator.computeDCG(RankEvaluationUtils.sortKeys(v3), v3), 20.0742, 0.0001);
	}
	
	@Test
	public void testAccumulator() {
		
		long[] items = {1, 2, 3, 4, 5, 6, 7 ,8, 9, 10};
		double[] ratings1 = {5, 4, 4, 3, 5, 3, 4, 3, 2, 5};
		double[] predictions1 = {5, 5, 4, 4, 4, 3, 2, 2, 3, 4};
		double[] ratings2 = {4, 5, 4, 2, 3, 1, 3, 4, 5, 2};
		double[] predictions2 = {4, 4, 5, 2, 3, 2, 3, 4, 4, 3};
		double[] ratings3 = {5, 4, 5, 3, 2, 3, 4, 5, 3, 5};
		double[] predictions3 = {4, 4, 5, 3, 3, 4, 5, 4, 4, 4};
		MutableSparseVector rate1 = MutableSparseVector.wrap(items,ratings1);
		MutableSparseVector predict1 = MutableSparseVector.wrap(items, predictions1);
		MutableSparseVector rate2 = MutableSparseVector.wrap(items, ratings2);
		MutableSparseVector predict2 = MutableSparseVector.wrap(items, predictions2);
		MutableSparseVector rate3 = MutableSparseVector.wrap(items, ratings3);
		MutableSparseVector predict3 = MutableSparseVector.wrap(items, predictions3);
		NDCGEvaluator.Accum acc = (new NDCGEvaluator()).makeAccumulator();
		acc.evaluatePredictions(1, rate1, predict1);
		assertEquals(acc.nusers, 1);
		assertEquals(acc.total, 0.9571, 0.0001);
		acc.evaluatePredictions(2, rate2, predict2);
		assertEquals(acc.nusers, 2);
		assertEquals(acc.total,1.9389, 0.0001);
		acc.evaluatePredictions(3, rate3, predict3);
		assertEquals(acc.nusers, 3);
		assertEquals(acc.total, 2.9327, 0.0001);
	}
}