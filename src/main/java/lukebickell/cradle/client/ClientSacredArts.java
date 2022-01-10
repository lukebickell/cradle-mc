package lukebickell.cradle.client;

import lukebickell.cradle.common.network.packet.ClientBoundUpdateSacredArtsPacket;
import lukebickell.cradle.common.ranks.SacredArtsRank;
import org.jetbrains.annotations.Nullable;

import static lukebickell.cradle.common.constants.SacredArtsContstants.*;

public class ClientSacredArts {

    private double maxMadra = INITIAL_MAX_MADRA;
    private double currentMadra = 0;
    private SacredArtsRank rank = SacredArtsRank.FOUNDATION;

    @Nullable
    private ClientSacredArts.Listener listener;

    public ClientSacredArts() {}

    public void update(ClientBoundUpdateSacredArtsPacket packet) {
        this.currentMadra = packet.sacredArts.getDouble(NBT_KEY_CRADLE_SPIRIT_CURRENT_MADRA);
        this.maxMadra = packet.sacredArts.getDouble(NBT_KEY_CRADLE_SPIRIT_MAX_MADRA);
        this.rank = SacredArtsRank.valueOf(packet.sacredArts.getString(NBT_KEY_CRADLE_SPIRIT_RANK));

        if (this.listener != null) {
            this.listener.onUpdateSacredArts(this.maxMadra, this.currentMadra, this.rank);
        }
    }

    public SacredArtsRank getRank() {
        return this.rank;
    }

    public boolean canRankUp() {
        return this.currentMadra == this.maxMadra;
    }

    public float getPercentMadraFull() {
        return (float) (this.currentMadra / this.maxMadra);
    }

    public void setListener(ClientSacredArts.Listener listener) {
        this.listener = listener;
        if (listener != null) {
            this.listener.onUpdateSacredArts(this.maxMadra, this.currentMadra, this.rank);
        }
    }

    public interface Listener {
        void onUpdateSacredArts(double maxMadra, double currentMadra, SacredArtsRank rank);
    }

}
