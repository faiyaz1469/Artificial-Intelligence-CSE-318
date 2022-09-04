class MancalaPlayersProfile
{
    String isARealPlayername;
    int mancalaPlayerNum;
    MancalaPlayersProfile(String name, int playerNum)
    {
        this.isARealPlayername = name;
        this.mancalaPlayerNum = playerNum;
    }

    public String getName()
    {
        if (isARealPlayername != null)
            return isARealPlayername;
        else {

            if(mancalaPlayerNum == 0)
                return "AI 1!";
            else
                return  "AI 2!";
        }
    }
}
