package com.epam.mjc;

import java.util.List;

public class MethodParser {
    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        if (signatureString == null || signatureString.isEmpty()) {
            throw new IllegalArgumentException("The value of SignatureString is wrong (input value is empty)");
        }

        int argumentsStartIndex = signatureString.indexOf("(");
        int argumentsFinishIndex = signatureString.indexOf(")");

        if (argumentsStartIndex == -1 || argumentsFinishIndex == -1 || argumentsStartIndex > argumentsFinishIndex) {
            throw new IllegalArgumentException("The value of SignatureString is wrong (argument braces not found)");
        }

        MethodSignature methodSignature = getMethodSignature(signatureString, argumentsStartIndex);
        parseAndLoadMethodArguments(signatureString, argumentsStartIndex, argumentsFinishIndex, methodSignature.getArguments());

        return methodSignature;
    }

    private MethodSignature getMethodSignature(String signatureString, int argumentsStartIndex) {
        String signatureStringWithoutArguments = signatureString.substring(0, argumentsStartIndex);

        String[] methodElementsWithoutArguments = signatureStringWithoutArguments.split("( +)");
        if (methodElementsWithoutArguments.length < 2) {
            throw new IllegalArgumentException("The value of SignatureString is wrong (not have method name or return type)");
        }
        String methodName = methodElementsWithoutArguments[methodElementsWithoutArguments.length-1];

        MethodSignature methodSignature = new MethodSignature(methodName);
        methodSignature.setReturnType(methodElementsWithoutArguments[methodElementsWithoutArguments.length-2]);

        if (methodElementsWithoutArguments.length > 2) {
            methodSignature.setAccessModifier(methodElementsWithoutArguments[methodElementsWithoutArguments.length - 3]);
        }
        return methodSignature;
    }

    private void parseAndLoadMethodArguments(
            String signatureString, int argumentsStartIndex, int argumentsFinishIndex, List<MethodSignature.Argument> list
    ) {
        String argumentsString = signatureString.substring(argumentsStartIndex + 1, argumentsFinishIndex);

        if (!argumentsString.isEmpty()) {
            String[] arguments = argumentsString.split(",");
            for (String argumentString : arguments) {
                String[] argumentParts = argumentString.trim().split("( +)");
                if (argumentParts.length != 2) {
                    throw new IllegalArgumentException("The value of SignatureString is wrong (incorrect arguments descriptions)");
                }
                MethodSignature.Argument argument = new MethodSignature.Argument(argumentParts[0], argumentParts[1]);
                list.add(argument);
            }
        }
    }
}
