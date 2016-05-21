package com.easy.common.core.api.impl;

import com.easy.common.core.api.IOperation;

public class PlusOperationImpl implements IOperation {
	
	public int operation(int numberA, int numberB) {
		return numberA + numberB;
	}
}
