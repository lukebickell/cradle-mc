package lukebickell.cradle.client;

import lukebickell.cradle.common.capability.SacredArtsImpl;
import lukebickell.cradle.common.network.packet.ClientBoundUpdateSacredArtsPacket;
import lukebickell.cradle.common.ranks.SacredArtsRank;
import org.jetbrains.annotations.Nullable;

import static lukebickell.cradle.common.capability.SacredArtsImpl.NBT_KEY_CRADLE_SPIRIT_CORE_SIZE;

public class ClientSacredArts {

    private int coreSize = 0;
    private SacredArtsRank rank = SacredArtsRank.FOUNDATION;

    @Nullable
    private ClientSacredArts.Listener listener;

    public ClientSacredArts() {}

    public void update(ClientBoundUpdateSacredArtsPacket packet) {
        this.coreSize = packet.sacredArts.getInt(NBT_KEY_CRADLE_SPIRIT_CORE_SIZE);
        this.rank = SacredArtsRank.valueOf(packet.sacredArts.getString(SacredArtsImpl.NBT_KEY_CRADLE_SPIRIT_RANK));

        if (this.listener != null) {
            this.listener.onUpdateSacredArts(this.coreSize, this.rank);
        }
    }

    public int getCoreSize() {
        return this.coreSize;
    }

    public SacredArtsRank getRank() {
        return this.rank;
    }

    public String getRankName() {
        return this.rank.name();
    }

    public void setListener(ClientSacredArts.Listener listener) {
        this.listener = listener;
        if (listener != null) {
            this.listener.onUpdateSacredArts(this.coreSize, this.rank);
        }
    }

    public interface Listener {
        void onUpdateSacredArts(int coreSize, SacredArtsRank rank);
    }

}
