package org.swampscottcurrents.serpentui;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField
{
    public NumericTextField() {

    }

    public NumericTextField(int number) {
        setText(new Integer(number).toString());
    }

    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    public int getNumber() {
        if(getText().equals("")) {
            return 0;
        }
        else {
            return Integer.parseInt(getText());
        }
    }

    private boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }
}