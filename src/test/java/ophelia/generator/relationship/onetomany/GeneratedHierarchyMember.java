package ophelia.generator.relationship.onetomany;

import ophelia.collections.set.UnmodifiableSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class GeneratedHierarchyMember {

    private final Set<GeneratedHierarchyMember> children = new  HashSet<>();

    @Nullable
    private GeneratedHierarchyMember parent;

    @NotNull
    public UnmodifiableSet<GeneratedHierarchyMember> getChildren() {
        return new  UnmodifiableSet<>(children);
    }

    public void addChild(final @NotNull GeneratedHierarchyMember child) {
        if (children.contains(child)) {
            return;
        }
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(final @NotNull GeneratedHierarchyMember child) {
        if (!children.contains(child)) {
            return;
        }
        children.remove(child);
        child.setParent(null);
    }

    public void removeAllChildren() {
        if (children.isEmpty()) {
            return;
        }
        new  HashSet<>(children).forEach( child->child.setParent(null));
    }

    @Nullable
    public GeneratedHierarchyMember getParent() {
        return parent;
    }

    public void setParent(final @Nullable GeneratedHierarchyMember parent) {
        if (parent == this.parent) {
            return;
        }
        if (this.parent != null) {
            this.parent.removeChild(this);
        }
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }
}
